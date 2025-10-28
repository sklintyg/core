package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;

class CodeSystemIcd10SeTest {

  @Test
  void testDeprecatedTerminology() {
    ElementDiagnosisTerminology terminology = CodeSystemIcd10Se.deprecatedTerminology();

    assertAll(
        () -> assertEquals("ICD_10_SE", terminology.id()),
        () -> assertEquals("ICD-10-SE", terminology.label()),
        () -> assertEquals("1.2.752.116.1.1.1.1.8", terminology.codeSystem()),
        () -> assertEquals(List.of("1.2.752.116.1.1.1", "1.2.752.116.1.1.1.1.3"),
            terminology.equivalentCodeSystems()));

  }

  @Test
  void testTerminology() {
    ElementDiagnosisTerminology terminology = CodeSystemIcd10Se.terminology();
    assertAll(
        () -> assertEquals("ICD_10_SE", terminology.id()),
        () -> assertEquals("ICD-10-SE", terminology.label()),
        () -> assertEquals("1.2.752.116.1.1.1", terminology.codeSystem()),
        () -> assertEquals(List.of("1.2.752.116.1.1.1.1.8", "1.2.752.116.1.1.1.1.3"),
            terminology.equivalentCodeSystems())
    );
  }
}