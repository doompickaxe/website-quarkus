package io.kay.website

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class PersonalResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/api/personal")
          .then()
             .statusCode(200)
    }
}
