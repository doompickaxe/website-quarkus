package io.kay.website

import io.kay.website.domain.City
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

    @BeforeEach
    fun beforeAll() {
        val db = Database.connect(dataSource)

        transaction(db) {
            val savedCountry = Country.new {
                name = "Country"
                code = "CC"
            }

            City.new {
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
}
