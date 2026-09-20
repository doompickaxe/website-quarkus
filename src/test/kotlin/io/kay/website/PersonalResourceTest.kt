package io.kay.website

import io.kay.website.domain.*
import io.kay.website.util.clearDB
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SizedCollection
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*
import javax.sql.DataSource

@QuarkusTest
class PersonalResourceTest {

    @Inject
    private lateinit var dataSource: DataSource

    private lateinit var personId: UUID

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
}
