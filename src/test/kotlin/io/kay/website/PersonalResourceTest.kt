package io.kay.website

import io.kay.website.domain.City
import io.kay.website.domain.Country
import io.kay.website.domain.Person
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
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

    @BeforeEach
    fun beforeAll() {
        val db = Database.connect(dataSource)

        transaction(db) {
            val countryId = Country.new {
                name = "Country"
                code = "CC"
            }.id

            val cityId = City.new {
                name = "City"
                country = countryId
            }.id

            Person.new {
                uuid = UUID.randomUUID()
                firstName = "firstName"
                lastName = "lastName"
                birthday = LocalDate.now()
                email = "email@example.com"
                phone = "01234"
                originalFrom = cityId
            }
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
    fun testHelloEndpoint() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons")
            .then()
            .statusCode(200)
            .body(
                "[0].id", notNullValue(),
                "[0].firstname", equalTo("firstName"),
                "[0].lastname", equalTo("lastName")
            )
    }
}
