package se.inera.intyg.cts.integrationtest;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import se.inera.intyg.cts.testability.dto.TestabilityTerminationDTO;

public class TestData {

  private final List<TestabilityTerminationDTO> terminationDTOs = new ArrayList<>();
  private final List<String> terminationIds = new ArrayList<>();

  public static TestData create() {
    return new TestData();
  }

  private TestData() {
  }

  public TestData defaultTermination() {
    defaultTerminations(1);
    return this;
  }

  public TestData defaultTerminations(int count) {
    for (int i = 0; i < count; i++) {
      terminationDTOs.add(
          new TestabilityTerminationDTO(
              UUID.randomUUID(),
              LocalDateTime.now(),
              "CREATORHSA-ID",
              "Creator Name",
              "CREATED",
              "HSA-ID",
              "ORG-NO",
              "191212121212",
              "000-1111-2222"
          )
      );
    }
    return this;
  }

  public TestData setup() {
    if (terminationDTOs.size() > 0) {
      for (TestabilityTerminationDTO testabilityTerminationDTO : terminationDTOs) {
        given()
            .contentType(ContentType.JSON)
            .body(testabilityTerminationDTO)
            .when()
            .post("/testability/v1/terminations")
            .then()
            .statusCode(HttpStatus.OK.value());

        terminationIds.add(testabilityTerminationDTO.terminationId().toString());
      }
    }
    return this;
  }

  public void cleanUp() {
    terminationIds.forEach(this::delete);
  }

  public TestData terminationId(String terminationId) {
    terminationIds.add(terminationId);
    return this;
  }

  public List<String> terminationIds() {
    return Collections.unmodifiableList(terminationIds);
  }

  private void delete(String terminationId) {
    given()
        .pathParam("terminationId", terminationId)
        .when()
        .delete("/testability/v1/terminations/{terminationId}")
        .then()
        .statusCode(HttpStatus.OK.value());
  }
}
