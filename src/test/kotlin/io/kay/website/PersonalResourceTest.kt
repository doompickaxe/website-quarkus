package io.kay.website

import io.kay.website.domain.*
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedCollection
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

            val localCity = City.new {
                name = "City"
                country = savedCountry
            }

            val language = Language.new {
                name = "English"
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

            Education.new {
                schoolName = "School"
                start = LocalDate.of(2024, 1, 1)
                end = LocalDate.of(2024, 2, 2)
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
                "languages", hasItem("English"),
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
    fun getCareerOfPerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/$personId/career")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(1),
                "[0].company.name", equalTo("test organization"),
                "[0].company.branch", equalTo("software testing"),
                "[0].company.city.country", equalTo("Country"),
                "[0].company.city.city", equalTo("City"),
                "[0].company.amountOfEmployees", equalTo(70),
                "[0].jobTitle", equalTo("service tester"),
                "[0].start", equalTo("2025-01-01"),
                "[0].end", nullValue(),
                "[0].jobDescription", equalTo("testing APIs"),
                "[0].tasks", equalTo("writing tests, implementing software, refactor"),
            )
    }

    @Test
    fun getEducationPathOfPerson() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .get("/api/persons/$personId/education")
            .then()
            .statusCode(200)
            .body(
                "$.size()", equalTo(1),
                "[0].schoolName", equalTo("School"),
                "[0].city.country", equalTo("Country"),
                "[0].city.city", equalTo("City"),
                "[0].start", equalTo("2024-01-01"),
                "[0].end", equalTo("2024-02-02"),
                "[0].degree", equalTo("service tester"),
                "[0].description", equalTo("special studies created by myself"),
                "[0].educationType", equalTo("UNIVERSITY"),
            )
    }
}
