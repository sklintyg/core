package se.inera.intyg.intygproxyservice.integration.api.elva77;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CitizenTest {

  @Test
  void shouldReturnInactiveCitizen() {
    final var personId = "personId";
    final var expectedCitizen = Citizen.builder()
        .subjectOfCareId(personId)
        .isActive(false)
        .build();

    assertEquals(expectedCitizen, Citizen.inactive(personId));
  }
}