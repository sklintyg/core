package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ElementDiagnosisTerminologyTest {

  private static final ElementDiagnosisTerminology TERMINOLOGY = new ElementDiagnosisTerminology(
      "id", "label", "primaryCodeSystem", List.of("equivalentCodeSystem"));

  @Test
  void shouldReturnTrueForValidCodeSystem() {

    assertAll(() -> assertTrue(TERMINOLOGY.isValidCodeSystem("primaryCodeSystem")),
        () -> assertTrue(TERMINOLOGY.isValidCodeSystem("equivalentCodeSystem")));
  }

  @Test
  void shouldReturnFalseForInvalidCodeSystem() {

    assertFalse(TERMINOLOGY.isValidCodeSystem("invalidCodeSystem"));
  }

  @Test
  void shouldHandleNullEquivalentCodeSystems() {
    ElementDiagnosisTerminology terminology = new ElementDiagnosisTerminology("id", "label",
        "primaryCodeSystem", null);

    assertNotNull(terminology.equivalentCodeSystems());
    assertTrue(terminology.equivalentCodeSystems().isEmpty());
  }

  @Test
  void shouldReturnTrueForPrimaryCodeSystem() {
    assertTrue(TERMINOLOGY.isValidCodeSystem("primaryCodeSystem"));
  }

  @Test
  void shouldReturnTrueForEquivalentCodeSystem() {
    assertTrue(TERMINOLOGY.isValidCodeSystem("equivalentCodeSystem"));
  }

  @Test
  void shouldReturnFalseForUnrelatedCodeSystem() {
    assertFalse(TERMINOLOGY.isValidCodeSystem("unrelatedCodeSystem"));
  }

  @Test
  void shouldReturnFalseForEmptyCodeSystem() {
    assertFalse(TERMINOLOGY.isValidCodeSystem(""));
  }

  @Test
  void shouldReturnFalseForNullCodeSystem() {
    assertFalse(TERMINOLOGY.isValidCodeSystem(null));
  }
}