package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;

class CodeSystemIcd10SeTest {

  @Test
  void testDeprecatedTerminology() {
    ElementDiagnosisTerminology terminology = CodeSystemIcd10Se.deprecatedTerminology();

    assertEquals("ICD_10_SE", terminology.id());
    assertEquals("ICD-10-SE", terminology.label());
    assertEquals("1.2.752.116.1.1.1.1.8", terminology.codeSystem());
  }

  @Test
  void testTerminology() {
    ElementDiagnosisTerminology terminology = CodeSystemIcd10Se.terminology();

    assertEquals("ICD_10_SE", terminology.id());
    assertEquals("ICD-10-SE", terminology.label());
    assertEquals("1.2.752.116.1.1.1", terminology.codeSystem());
  }

  @Test
  void testAcceptedCodeSystemOids() {
    List<String> oids = CodeSystemIcd10Se.acceptedCodeSystemOids();

    assertEquals(3, oids.size());
    assertTrue(oids.contains("1.2.752.116.1.1.1"));
    assertTrue(oids.contains("1.2.752.116.1.1.1.1.8"));
    assertTrue(oids.contains("1.2.752.116.1.1.1.1.3"));
  }
}