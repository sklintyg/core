package se.inera.intyg.cts.integrationtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;

public class TerminationIT {

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
  void shallCreateTermination() {
    testData
        .setup();

    final var createTerminationDTO = new CreateTerminationDTO(
        "CREATORHSA-ID",
        "Creator Name",
        "HSA-ID",
        "ORG-NO",
        "191212121212",
        "000-1111-2222"
    );

    final var terminationDTO =
        given()
            .contentType(ContentType.JSON)
            .body(createTerminationDTO)
            .when()
            .post("/api/v1/terminations")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            .extract()
            .response().as(TerminationDTO.class);

    testData.terminationId(terminationDTO.terminationId().toString());

    assertNotNull(terminationDTO.terminationId());
  }

  @Test
  void shallReturnTermination() {
    testData
        .defaultTermination()
        .setup();

    final var terminationDTO =
        given()
            .pathParam("terminationId", testData.terminationIds().get(0))
            .when()
            .get("/api/v1/terminations/{terminationId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .response().as(TerminationDTO.class);

    assertEquals(testData.terminationIds().get(0), terminationDTO.terminationId().toString());
  }

  @Test
  void shallReturnTerminations() {
    testData
        .defaultTerminations(10)
        .setup();

    final var terminationDTO =
        given()
            .when()
            .get("/api/v1/terminations")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .response().as(TerminationDTO[].class);

    assertEquals(10, terminationDTO.length);
  }
}
