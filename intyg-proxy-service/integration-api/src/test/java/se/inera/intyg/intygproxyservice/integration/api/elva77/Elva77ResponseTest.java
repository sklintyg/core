package se.inera.intyg.intygproxyservice.integration.api.elva77;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Elva77ResponseTest {

  @Nested
  class ErrorTests {

    @Test
    void shouldReturnResponseWithResultError() {
      assertEquals(Result.ERROR, Elva77Response.error().getResult());
    }

    @Test
    void shouldReturnResponseWithoutCitizen() {
      assertNull(Elva77Response.error().getCitizen());
    }
  }

  @Nested
  class InactiveTests {

    @Test
    void shouldReturnResponseWithResultInfo() {
      assertEquals(Result.INFO, Elva77Response.inactive(null).getResult());
    }

    @Test
    void shouldReturnResponseWithInactiveCitizen() {
      final var subjectOfCareId = "personId";
      final var expectedCitizen = Citizen.builder()
          .subjectOfCareId(subjectOfCareId)
          .isActive(false)
          .build();

      assertEquals(expectedCitizen, Elva77Response.inactive(subjectOfCareId).getCitizen());
    }
  }
}