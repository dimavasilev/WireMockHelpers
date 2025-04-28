package services;

import static io.restassured.RestAssured.given;

import dto.User;
import io.restassured.specification.RequestSpecification;


public class UserServiceClient {

  private final RequestSpecification requestSpecification;

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
