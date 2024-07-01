package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;

class ElementConfigurationDiagnosisTest {

  private static final String ID = "id";

  @Test
  void shallReturnEmptyValue() {
    final var expectedValue = ElementValueDiagnosisList.builder()
        .diagnoses(Collections.emptyList())
        .build();
    final var configurationDiagnosis = ElementConfigurationDiagnosis.builder().build();
    assertEquals(expectedValue, configurationDiagnosis.emptyValue());
  }

  @Test
  void shallReturnCodeSystem() {
    final var expectedCodeSystem = "expectedCodeSystem";
    final var configurationDiagnosis = ElementConfigurationDiagnosis.builder()
        .terminology(
            List.of(
                new ElementDiagnosisTerminology(ID, "label", expectedCodeSystem)
            )
        )
        .build();
    assertEquals(expectedCodeSystem, configurationDiagnosis.codeSystem(ID));
  }
}
