package services;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;


public class CourseServiceClient {

  private final RequestSpecification requestSpecification;

  public CourseServiceClient() {
    requestSpecification = given()
        .baseUri(System.getProperty("base.url"))
        .contentType("application/json");
  }

  public String getCourseInfo() {
    return given(this.requestSpecification)
        .get("/course")
        .getBody().prettyPrint();
  }
}
