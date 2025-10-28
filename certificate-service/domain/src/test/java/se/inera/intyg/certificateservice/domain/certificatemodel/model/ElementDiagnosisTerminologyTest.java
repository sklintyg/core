package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ElementDiagnosisTerminologyTest {

  private static final ElementDiagnosisTerminology TERMINOLOGY = new ElementDiagnosisTerminology(
      "id", "label", "primaryCodeSystem", List.of("equivalentCodeSystem"));

  @Test
  void shouldHandleNullEquivalentCodeSystems() {
    ElementDiagnosisTerminology terminology = new ElementDiagnosisTerminology("id", "label",
        "primaryCodeSystem", null);

    assertNotNull(terminology.equivalentCodeSystems());
    assertTrue(terminology.equivalentCodeSystems().isEmpty());
  }

  @ParameterizedTest
  @ValueSource(strings = {"primaryCodeSystem", "equivalentCodeSystem"})
  void shouldReturnTrueForValidCodeSystem(String codeSystem) {
    assertTrue(TERMINOLOGY.isValidCodeSystem(codeSystem));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnFalseForInvalidCodeSystem(String codeSystem) {
    assertFalse(TERMINOLOGY.isValidCodeSystem(codeSystem));
  }

  @Test
  void shouldReturnFalseForUnrelatedCodeSystem() {
    assertFalse(TERMINOLOGY.isValidCodeSystem("unrelatedCodeSystem"));
  }
}