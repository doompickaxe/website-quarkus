package io.kay.website

import io.kay.website.domain.*
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
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
class EducationItemsTest {

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

            Education.new {
                schoolName = "Elementary School"
                start = LocalDate.of(2000, 1, 1)
                end = LocalDate.of(2000, 2, 2)
                degree = "Elementary Degree"
                description = "preparing for further schools"
                educationType = EducationType.ELEMENTARY_SCHOOL
                city = localCity
                person = savedPerson
            }

            Education.new {
                schoolName = "University"
                start = LocalDate.of(2000, 2, 1)
                end = LocalDate.of(2020, 2, 2)
                degree = "service tester"
                description = "special studies created by myself"
                educationType = EducationType.UNIVERSITY
                city = localCity
                person = savedPerson
            }
        }
    }

    @AfterEach
    fun afterAll() {
        val db = Database.connect(dataSource)

        transaction(db) {
            Education.all().forEach { it.delete() }
            Career.all().forEach { it.delete() }
            Company.all().forEach { it.delete() }
            Person.all().forEach { it.delete() }
            City.all().forEach { it.delete() }
            Country.all().forEach { it.delete() }
            Language.all().forEach { it.delete() }
            Interests.all().forEach { it.delete() }
        }
    }

    @Test
    fun getEducationPathOfPerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/$personId/education")
            .then()
            .statusCode(200)
            .log().everything()
            .body(
                "$.size()", equalTo(2),
                "[0].schoolName", equalTo("Elementary School"),
                "[0].city.country", equalTo("Country"),
                "[0].city.city", equalTo("City"),
                "[0].start", equalTo("2000-01-01"),
                "[0].end", equalTo("2000-02-02"),
                "[0].degree", equalTo("Elementary Degree"),
                "[0].description", equalTo("preparing for further schools"),
                "[0].educationType", equalTo("ELEMENTARY_SCHOOL"),
                "[1].schoolName", equalTo("University"),
                "[1].city.country", equalTo("Country"),
                "[1].city.city", equalTo("City"),
                "[1].start", equalTo("2000-02-01"),
                "[1].end", equalTo("2020-02-02"),
                "[1].degree", equalTo("service tester"),
                "[1].description", equalTo("special studies created by myself"),
                "[1].educationType", equalTo("UNIVERSITY"),
            )
    }

    @Test
    fun updateEducationPathOfPerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .body(File(javaClass.getResource("/requests/educationItems.json").file))
            .put("/api/persons/${UUID.randomUUID()}/education")
            .then()
            .statusCode(404)
            .log().everything()

        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .body(File(javaClass.getResource("/requests/educationItems.json").file))
            .put("/api/persons/$personId/education")
            .then()
            .log().everything()
            .statusCode(200)
            .body(
                "$.size()", equalTo(2),
                "[0].schoolName", equalTo("Making people smart"),
                "[0].city.country", equalTo("Country"),
                "[0].city.city", equalTo("City"),
                "[0].start", equalTo("2000-01-01"),
                "[0].end", equalTo("2000-02-02"),
                "[0].degree", equalTo("Elementary Degree"),
                "[0].description", equalTo("preparing for further schools"),
                "[0].educationType", equalTo("ELEMENTARY_SCHOOL"),
                "[1].schoolName", equalTo("Making people very smart"),
                "[1].city.country", equalTo("Country"),
                "[1].city.city", equalTo("City"),
                "[1].start", equalTo("2010-10-01"),
                "[1].end", nullValue(),
                "[1].degree", equalTo("Bachelor of Science"),
                "[1].description", equalTo("special studies created by myself"),
                "[1].educationType", equalTo("UNIVERSITY"),
            )
    }

    @Test
    fun deniesInvalidDates() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .body(File(javaClass.getResource("/requests/educationItemInvalid.json").file))
            .put("/api/persons/$personId/education")
            .then()
            .statusCode(400)
            .log().everything()
    }
}
