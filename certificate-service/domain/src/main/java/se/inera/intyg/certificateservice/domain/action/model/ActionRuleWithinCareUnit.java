package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleWithinCareUnit implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return certificate
        .filter(value ->
            isIssuingUnitMatchingSubUnit(actionEvaluation, value)
                || isCareUnitMatchingSubUnit(actionEvaluation, value)
        )
        .isPresent();
  }

  private static boolean isCareUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().careUnit().hsaId().equals(
        actionEvaluation.subUnit().hsaId()
    );
  }

  private static boolean isIssuingUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().issuingUnit().hsaId().equals(
        actionEvaluation.subUnit().hsaId()
    );
  }
}
