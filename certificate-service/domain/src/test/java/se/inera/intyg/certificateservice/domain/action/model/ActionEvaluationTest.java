package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_MEDICINSKT_CENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActionEvaluationTest {

  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  @BeforeEach
  void setUp() {
    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);
  }

  @Test
  void shallReturnTrueIfSubUnitIdMatchesCareUnitId() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(ALFA_MEDICINSKT_CENTRUM)
        .build();

    assertTrue(actionEvaluation.isIssuingUnitCareUnit());
  }

  @Test
  void shallReturnFalseIfSubUnitIdNotMatchingCareUnitId() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    assertFalse(actionEvaluation.isIssuingUnitCareUnit());
  }
}
