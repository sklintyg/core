package se.inera.intyg.certificateservice.domain.patient.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NameTest {

  @Test
  void shallCombineFirstMiddleLastToFullName() {
    final var expectedFullName = "Tolvan TP Tolvansson";

    final var name = Name.builder()
        .firstName("Tolvan")
        .middleName("TP")
        .lastName("Tolvansson")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineFirstLastToFullName() {
    final var expectedFullName = "Tolvan Tolvansson";

    final var name = Name.builder()
        .firstName("Tolvan")
        .lastName("Tolvansson")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineMiddleLastToFullName() {
    final var expectedFullName = "TP Tolvansson";

    final var name = Name.builder()
        .middleName("TP")
        .lastName("Tolvansson")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineFirstMiddleToFullName() {
    final var expectedFullName = "Tolvan TP";

    final var name = Name.builder()
        .firstName("Tolvan")
        .middleName("TP")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineLastToFullName() {
    final var expectedFullName = "Tolvansson";

    final var name = Name.builder()
        .lastName("Tolvansson")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineFirstToFullName() {
    final var expectedFullName = "Tolvan";

    final var name = Name.builder()
        .firstName("Tolvan")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }

  @Test
  void shallCombineMiddleToFullName() {
    final var expectedFullName = "TP";

    final var name = Name.builder()
        .middleName("TP")
        .build();

    assertEquals(expectedFullName, name.fullName());
  }
}