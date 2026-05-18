package io.kay.website

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.keycloak.client.KeycloakTestClient
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import java.util.*

@QuarkusTest
class AuthenticationTest {

    private val keycloakClient = KeycloakTestClient()

    @Test
    fun deniesAccessWithoutToken() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .body("{}")
            .put("/api/persons/${UUID.randomUUID()}/education")
            .then()
            .statusCode(401)
            .log().everything()
    }

    @Test
    fun allowsAccessWithToken() {
        given()
            .`when`()
            .header("Accept", "application/json")
            .auth().oauth2(keycloakClient.getRealmClientAccessToken("quarkus", "backend-service", "secret"))
            .header("Content-Type", "application/json")
            .body("{}")
            .put("/api/persons/${UUID.randomUUID()}/education")
            .then()
            .statusCode(400) // bad request, but able to access route
            .log().everything()
    }
}
