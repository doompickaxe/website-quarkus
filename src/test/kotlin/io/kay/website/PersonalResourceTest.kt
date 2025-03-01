package io.kay.website

import io.quarkus.test.junit.QuarkusIntegrationTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test

@QuarkusIntegrationTest
class PersonalResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/api/persons")
            .then()
            .statusCode(200)
    }
}
