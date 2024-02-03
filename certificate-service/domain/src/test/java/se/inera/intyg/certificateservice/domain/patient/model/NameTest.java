package se.inera.intyg.certificateservice.domain.patient.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;

import org.junit.jupiter.api.Test;

class NameTest {

  @Test
  void shallCombineFirstMiddleLastToFullName() {
    final var expectedFullName = "%s %s %s".formatted(
        ATHENA_REACT_ANDERSSON_FIRST_NAME,
        ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
        ATHENA_REACT_ANDERSSON_LAST_NAME
    );

    final var name = Name.builder()
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineFirstLastToFullName() {
    final var expectedFullName = "%s %s".formatted(
        ATHENA_REACT_ANDERSSON_FIRST_NAME,
        ATHENA_REACT_ANDERSSON_LAST_NAME
    );

    final var name = Name.builder()
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineMiddleLastToFullName() {
    final var expectedFullName = "%s %s".formatted(
        ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
        ATHENA_REACT_ANDERSSON_LAST_NAME
    );

    final var name = Name.builder()
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineFirstMiddleToFullName() {
    final var expectedFullName = "%s %s".formatted(
        ATHENA_REACT_ANDERSSON_FIRST_NAME,
        ATHENA_REACT_ANDERSSON_MIDDLE_NAME
    );

    final var name = Name.builder()
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineLastToFullName() {
    final var expectedFullName = "%s".formatted(
        ATHENA_REACT_ANDERSSON_LAST_NAME
    );

    final var name = Name.builder()
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineFirstToFullName() {
    final var expectedFullName = "%s".formatted(
        ATHENA_REACT_ANDERSSON_FIRST_NAME
    );

    final var name = Name.builder()
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineMiddleToFullName() {
    final var expectedFullName = "%s".formatted(
        ATHENA_REACT_ANDERSSON_MIDDLE_NAME
    );

    final var name = Name.builder()
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .build();

    assertEquals(expectedFullName, name.fullName());
  }
}