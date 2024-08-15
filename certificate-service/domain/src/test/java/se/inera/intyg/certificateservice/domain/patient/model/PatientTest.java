package se.inera.intyg.certificateservice.domain.patient.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

class PatientTest {

  @Test
  void shouldReturnAge0() {
    final var patient = Patient.builder()
        .id(
            PersonId.builder()
                .id("20240101-1111")
                .build()
        )
        .build();

    assertEquals(0, patient.getAge());
  }

  @Test
  void shouldReturnAge1() {
    final var patient = Patient.builder()
        .id(
            PersonId.builder()
                .id("20230101-1111")
                .build()
        )
        .build();

    assertEquals(1, patient.getAge());
  }

  @Test
  void shouldReturnAge10() {
    final var patient = Patient.builder()
        .id(
            PersonId.builder()
                .id("20140101-1111")
                .build()
        )
        .build();

    assertEquals(10, patient.getAge());
  }
}
