package se.inera.intyg.certificateservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CertificateActionCreateTest {

  private CertificateActionCreate certificateActionCreate;

  @BeforeEach
  void setUp() {
    certificateActionCreate = new CertificateActionCreate();
  }

  @Test
  void shallReturnName() {
    assertEquals("Skapa intyg", certificateActionCreate.getName());
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.CREATE, certificateActionCreate.getType());
  }

  @Test
  void shallReturnDescription() {
    assertEquals("Skapa ett intygsutkast", certificateActionCreate.getDescription());
  }

  @Test
  void shallReturnFalseIfPatientIsDeceased() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(true)
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfPatientIsNotDeceased() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(false)
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }
}