package org.ibs;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestTest {


    @Test
    void postTest() {
        Response response = given()
                .baseUri("http://localhost:8080")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \"Лук\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .when()
                .post("/api/food");
        response.then()
                .assertThat()
                .log().all()
                .statusCode(200);

        Map<String, String> cookies = response.then()
                .extract()
                .cookies();

        given()
                .baseUri("http://localhost:8080")
                .cookies(cookies)
                .when()
                .get("/api/food");

        response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .body();
        String a = response.getBody().asPrettyString();
        a.contains("Лук");



    }

    @Test
    void getTest() {

        given()
                .baseUri("http://localhost:8080")
                .when()
                .log().all()
                .get("api/food")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

}


