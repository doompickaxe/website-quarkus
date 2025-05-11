package io.kay.website

import io.kay.website.domain.City
import io.kay.website.domain.Country
import io.kay.website.domain.Person
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
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

            val city = City.new {
                name = "City"
                country = savedCountry
            }

            Person.new {
                uuid = UUID.randomUUID()
                firstName = "firstName"
                lastName = "lastName"
                birthday = LocalDate.now().minusYears(5)
                email = "email@example.com"
                phone = "01234"
                originalFrom = city
            }.let { personId = it.uuid }
        }
    }

    @AfterEach
    fun afterAll() {
        val db = Database.connect(dataSource)

        transaction(db) {
            Person.all().forEach { it.delete() }
            City.all().forEach { it.delete() }
            Country.all().forEach { it.delete() }
        }
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
