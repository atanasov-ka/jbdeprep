package org.vb.backend.rest;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.*;

public class Tests {
    private Util util = new Util();

    @Test
    public void loginSuccessFully() throws IOException, URISyntaxException {
        String requestBody = util.readResourceFile("/userValidRequest.json");

        given()
            .body(requestBody)
            .header("content-type", "application/json")
        .when()
            .post("http://localhost:8081/vb/api/users/authenticate")
        .then()
            .statusCode(200);
    }

    @Test
    public void loginFail() throws IOException, URISyntaxException {
        String requestBody = util.readResourceFile("/userInvalidRequest.json");

        given()
            .body(requestBody)
            .header("content-type", "application/json")
        .when()
            .post("http://localhost:8081/vb/api/users/authenticate")
        .then()
            .statusCode(401);
    }
}
