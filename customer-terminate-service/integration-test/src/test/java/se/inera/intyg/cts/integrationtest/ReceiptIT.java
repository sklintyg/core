package se.inera.intyg.cts.integrationtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.infrastructure.persistence.entity.ExportEmbeddable;

public class ReceiptIT {

  private TestData testData;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = System.getProperty("integration.tests.baseUrl",
        "http://cts.localtest.me");
    testData = TestData.create();
  }

  @AfterEach
  void tearDown() {
    testData.cleanUp();
    RestAssured.reset();
  }

  @Test
  public void shallUpdateStatusWhenReceiptReceived() {
    testData.defaultTermination().setup();

    final var terminationId = testData.terminationIds().get(0);

    given()
        .pathParam("terminationId", terminationId)
        .when().post("/api/v1/receipt/{terminationId}")
        .then().statusCode(HttpStatus.OK.value());

    final var terminationDTO = given()
        .pathParam("terminationId", terminationId)
        .when().get("/api/v1/terminations/{terminationId}")
        .then().statusCode(HttpStatus.OK.value())
        .extract().response().as(TerminationDTO.class);

    assertEquals(TerminationStatus.RECEIPT_RECEIVED.description(), terminationDTO.status());
  }

  @Test
  public void shallSetReceiptTime() {
    testData.defaultTermination().setup();

    final var terminationId = testData.terminationIds().get(0);

    given()
        .pathParam("terminationId", terminationId)
        .when().post("/api/v1/receipt/{terminationId}")
        .then().statusCode(HttpStatus.OK.value());

    final var export = given()
        .pathParam("terminationId", terminationId)
        .when().get("/testability/v1/terminations/export/{terminationId}")
        .then().statusCode(HttpStatus.OK.value())
        .extract().response().as(ExportEmbeddable.class);

    assertNotNull(export.getReceiptTime());
  }

  @Test
  public void shallReturnNotFoundIfReceiptForNonExistingTermination() {
    testData.defaultTermination().setup();

    given()
        .pathParam("terminationId", UUID.randomUUID())
        .when().post("/api/v1/receipt/{terminationId}")
        .then().statusCode(HttpStatus.NOT_FOUND.value());
  }
}
