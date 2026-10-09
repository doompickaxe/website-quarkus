package io.kay.website

import io.kay.website.domain.*
import io.kay.website.util.clearDB
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.keycloak.client.KeycloakTestClient
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SizedCollection
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import java.time.Period
import java.util.*
import javax.sql.DataSource

@QuarkusTest
class PersonalResourceTest {

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
                uuid = UUID.randomUUID()
            }

            val language = Language.new {
                name = "English"
            }

            val codingInterest = Interests.new {
                name = "Coding"
            }
            val cookingInterest = Interests.new {
                name = "Cooking"
            }

            val savedPerson = Person.new {
                uuid = UUID.randomUUID()
                firstName = "firstName"
                lastName = "lastName"
                birthday = LocalDate.now().minusYears(5)
                email = "email@example.com"
                phone = "01234"
                originalFrom = localCity
                languages = SizedCollection(language)
                interests = SizedCollection(codingInterest, cookingInterest)
            }
            personId = savedPerson.uuid

            val newCompany = Company.new {
                name = "test organization"
                branch = "software testing"
                city = localCity
                amountOfEmployees = 70
                uuid = UUID.randomUUID()
            }

            Career.new {
                company = newCompany
                start = LocalDate.of(2025, 1, 1)
                jobTitle = "service tester"
                jobDescription = "testing APIs"
                tasks = "writing tests, implementing software, refactor"
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
    fun getAllPersons() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons")
            .then()
            .statusCode(200)
            .body(
                "[0].id", equalTo(personId.toString()),
                "[0].firstName", equalTo("firstName"),
                "[0].lastName", equalTo("lastName"),
            )
    }

    @Test
    fun getOnePerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/$personId")
            .then()
            .statusCode(200)
            .body(
                "firstName", equalTo("firstName"),
                "lastName", equalTo("lastName"),
                "age", equalTo(5),
                "email", equalTo("email@example.com"),
                "phoneNumber", equalTo("01234"),
                "originalFrom.city", equalTo("City"),
                "originalFrom.country", equalTo("Country"),
                "currentlyLivingIn", nullValue(),
                "languages", hasItem("English"),
                "interests", hasItems("Coding", "Cooking"),
            )

        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/unknown")
            .then()
            .statusCode(404)

        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/${UUID.randomUUID()}")
            .then()
            .statusCode(404)
    }

    @Test
    fun updateOnePerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/person/person.json").file))
            .put("/api/persons/$personId")
            .then()
            .statusCode(200)
            .body(
                "firstName", equalTo("Max"),
                "lastName", equalTo("Mustermann"),
                // age changes every year
                "age", equalTo(Period.between(LocalDate.of(1966, 8, 23), LocalDate.now()).years),
                "email", equalTo("sample@email.com"),
                "phoneNumber", equalTo("+43618999997"),
                "originalFrom.city", equalTo("City"),
                "originalFrom.country", equalTo("Country"),
                "currentlyLivingIn.city", equalTo("City"),
                "currentlyLivingIn.country", equalTo("Country"),
                "languages", hasItems("English", "German"),
                "interests", hasItems("Writing OpenAPI specs", "Executing tests"),
            )

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/person/person.json").file))
            .put("/api/persons/unknown")
            .then()
            .statusCode(404)

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .body(File(javaClass.getResource("/requests/person/person.json").file))
            .put("/api/persons/${UUID.randomUUID()}")
            .then()
            .statusCode(404)

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .put("/api/persons/$personId")
            .then()
            .statusCode(400)
    }
}
