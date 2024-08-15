package se.inera.intyg.certificateservice.domain.patient.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

class PatientTest {

  @Test
  void shouldReturnAge0() {
    final var patient = Patient.builder()
        .id(
            PersonId.builder()
                .id(LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE) + "-0101")
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
                .id(LocalDate.now().minusDays(1).minusYears(1)
                    .format(DateTimeFormatter.BASIC_ISO_DATE) + "-0101")
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
                .id(LocalDate.now().minusDays(1).minusYears(10)
                    .format(DateTimeFormatter.BASIC_ISO_DATE) + "-0101").build()
        )
        .build();

    assertEquals(10, patient.getAge());
  }
}
