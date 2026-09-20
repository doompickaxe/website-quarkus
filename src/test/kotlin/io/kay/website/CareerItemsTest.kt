package io.kay.website

import io.kay.website.domain.*
import io.kay.website.util.clearDB
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.keycloak.client.KeycloakTestClient
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SizedCollection
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import java.util.*
import javax.sql.DataSource

@QuarkusTest
class CareerItemsTest {

    @Inject
    private lateinit var dataSource: DataSource

    private lateinit var personId: UUID
    private val keycloakClient = KeycloakTestClient()

    @BeforeEach
    fun beforeAll() {
        val db = Database.connect(dataSource)

        transaction(db) {
            val savedCountry = Country.new {
                name = "Country"
                code = "CC"
            }

            val localCity = City.new {
                name = "City"
                country = savedCountry
            }

            val savedPerson = Person.new {
                uuid = UUID.randomUUID()
                firstName = "firstName"
                lastName = "lastName"
                birthday = LocalDate.now().minusYears(5)
                email = "email@example.com"
                phone = "01234"
                originalFrom = localCity
                languages = SizedCollection()
                interests = SizedCollection()
            }
            personId = savedPerson.uuid

            val savedCompany = Company.new {
                name = "test organization"
                branch = "software testing"
                city = localCity
                amountOfEmployees = 70
            }

            Career.new {
                company = savedCompany
                start = LocalDate.of(2000, 1, 1)
                end = LocalDate.of(2000, 2, 2)
                jobTitle = "Test writer"
                tasks = "Working and doing work"
                jobDescription = "Writing tests"
                person = savedPerson
            }

            Career.new {
                company = savedCompany
                start = LocalDate.of(2005, 1, 1)
                jobTitle = "Test writer"
                tasks = "Working and doing even more work"
                jobDescription = "Writing more tests"
                person = savedPerson
            }
        }
    }

    @AfterEach
    fun afterAll() {
        val db = Database.connect(dataSource)
        clearDB(db)
    }

    @Test
    fun getCareerPathOfPerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/$personId/career")
            .then()
            .statusCode(200)
            .log().everything()
            .body(
                "$.size()", equalTo(2),
                "[0].company.name", equalTo("test organization"),
                "[0].company.branch", equalTo("software testing"),
                "[0].company.city.country", equalTo("Country"),
                "[0].company.city.city", equalTo("City"),
                "[0].company.amountOfEmployees", equalTo(70),
                "[0].start", equalTo("2000-01-01"),
                "[0].end", equalTo("2000-02-02"),
                "[0].jobTitle", equalTo("Test writer"),
                "[0].jobDescription", equalTo("Writing tests"),
                "[0].tasks", equalTo("Working and doing work"),
                "[1].company.name", equalTo("test organization"),
                "[1].start", equalTo("2005-01-01"),
                "[1].jobTitle", equalTo("Test writer"),
                "[1].jobDescription", equalTo("Writing more tests"),
                "[1].tasks", equalTo("Working and doing even more work"),
            )
    }

    @Test
    fun updateCareerPathOfPerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/careerItems.json").file))
            .put("/api/persons/${UUID.randomUUID()}/career")
            .then()
            .statusCode(404)
            .log().everything()

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/careerItems.json").file))
            .put("/api/persons/$personId/career")
            .then()
            .log().everything()
            .statusCode(200)
            .body(
                "$.size()", equalTo(2),
                "[0].company.name", equalTo("test organization"),
                "[0].company.branch", equalTo("software testing"),
                "[0].company.city.country", equalTo("Country"),
                "[0].company.city.city", equalTo("City"),
                "[0].company.amountOfEmployees", equalTo(70),
                "[0].start", equalTo("2000-10-01"),
                "[0].end", equalTo("2010-09-01"),
                "[0].jobTitle", equalTo("OpenAPI writer"),
                "[0].jobDescription", equalTo("Backend Developer"),
                "[0].tasks", equalTo("Writing OpenAPI specifications"),
                "[1].company.name", equalTo("test organization"),
                "[1].start", equalTo("2011-01-01"),
                "[1].jobTitle", equalTo("OpenAPI writer"),
                "[1].jobDescription", equalTo("Better Backend Developer"),
                "[1].tasks", equalTo("Writing more OpenAPI specifications"),
            )
    }

    @Test
    fun deniesInvalidDates() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/careerItemInvalid.json").file))
            .put("/api/persons/$personId/career")
            .then()
            .statusCode(400)
            .body("message", equalTo("Career items end is before start"))
            .log().everything()
    }
}
