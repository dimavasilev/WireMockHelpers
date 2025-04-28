package services;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;

public class ScoreServiceClient {

  private final RequestSpecification requestSpecification;

  public ScoreServiceClient() {
    requestSpecification = given()
        .baseUri(System.getProperty("base.url"))
        .contentType("application/json");
  }

  public String getScoreInfo() {
    return given(this.requestSpecification)
        .get("/score")
        .getBody().prettyPrint();
  }
}
