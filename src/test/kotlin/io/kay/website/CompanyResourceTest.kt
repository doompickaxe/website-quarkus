package io.kay.website

import io.kay.website.domain.City
import io.kay.website.domain.Company
import io.kay.website.domain.Country
import io.kay.website.util.clearDB
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.keycloak.client.KeycloakTestClient
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import javax.sql.DataSource

@QuarkusTest
class CompanyResourceTest {

    @Inject
    private lateinit var dataSource: DataSource

    private val keycloakClient = KeycloakTestClient()

    private lateinit var db: Database
    private lateinit var globalCity: City

    @BeforeEach
    fun beforeAll() {
        db = Database.connect(dataSource)

        transaction(db) {
            val savedCountry = Country.new {
                name = "Country"
                code = "CC"
            }

            globalCity = City.new {
                name = "City"
                country = savedCountry
            }
        }
    }

    @AfterEach
    fun afterAll() {
        val db = Database.connect(dataSource)
        clearDB(db)
    }

    @Test
    fun createNewCompanyWithoutBody() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .post("/api/companies")
            .then()
            .statusCode(401)

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .post("/api/companies")
            .then()
            .statusCode(400)
            .body(
                "message", equalTo("Company is null"),
            )
    }

    @Test
    fun createCompanyTwice() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/company.json").file))
            .post("/api/companies")
            .then()
            .statusCode(201)
            .body(
                "name", equalTo("test organization"),
                "branch", equalTo("software testing"),
                "city.country", equalTo("Country"),
                "city.city", equalTo("City"),
                "amountOfEmployees", equalTo(70),
            )

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/company.json").file))
            .post("/api/companies")
            .then()
            .statusCode(400)
            .body(
                "message", equalTo("Company test organization already exists"),
            )
    }

    @Test
    fun retrieveCompanies() {
        // create 3 test companies
        transaction(db) {
            Company.new {
                name = "first company"
                branch = "software testing"
                city = globalCity
                amountOfEmployees = 1
            }

            Company.new {
                name = "second company"
                branch = "hardware testing"
                city = globalCity
                amountOfEmployees = 2
            }

            Company.new {
                name = "last company"
                branch = "test testing"
                city = globalCity
                amountOfEmployees = 42
            }
        }

        // without search
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(3),
            )

        // filters to 'first'
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies?name=first")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(1),
                "[0].name", equalTo("first company"),
            )

        // filters to 'last'
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies?name=last")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(1),
                "[0].name", equalTo("last company"),
            )

        // filters to 'company'
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies?name=company")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(3),
            )

        // sql injection safe
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies?name=nothing' OR '1'='1")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(0),
            )

        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies?name=% || (SELECT id FROM cities LIMIT 1) || '")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(0),
            )

        given()
            .`when`()
            .header("Accept", "application/json")
            .queryParam("name", "%' || (pg_sleep(300)) || %'%")
            .get("/api/companies")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(0),
            )

        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/companies?name=\''DROP TABLE company;--")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(0),
            )

        transaction(db) {
            assert(Company.all().count() == 3L)
        }

        // handles too long query parameter
        given()
            .`when`()
            .header("Accept", "application/json")
            .queryParam("name", (0..300).joinToString("") { "a" })
            .get("/api/companies")
            .then()
            .statusCode(400)
            .body(
                "violations[0].message", equalTo("size must be between 0 and 100"),
            )
    }
}
