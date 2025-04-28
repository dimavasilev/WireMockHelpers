package services;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserServiceClient {


    private RequestSpecification requestSpecification;

    public UserServiceClient() {
        requestSpecification = given()
                .baseUri(System.getProperty("base.url"))
                .baseUri("/user")
                .contentType("application/json");
    }

    public User getUserInfo() {
        return given(this.requestSpecification)
                .get("/user")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
    }
}
