package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;

public class TestDataAction {

  public static final ActionEvaluation ACTION_EVALUATION = actionEvaluationBuilder().build();

  public static ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder() {
    return ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN)
        .patient(ATHENA_REACT_ANDERSSON);
  }
}
