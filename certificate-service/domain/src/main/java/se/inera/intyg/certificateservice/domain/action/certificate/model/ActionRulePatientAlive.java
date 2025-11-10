package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Objects;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRulePatientAlive implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {

    if (actionEvaluationHasPatient(actionEvaluation)) {
      return actionEvaluation.filter(
              evaluation -> !evaluation.patient().deceased().value()
          )
          .isPresent();
    }
    if (certificateHasPatient(certificate)) {
      return certificate.filter(
              cert -> !cert.certificateMetaData().patient().deceased().value()
          )
          .isPresent();
    }

    return true;
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Du kan ej utföra den begärda åtgärden eftersom patienten är avliden.";
  }

  private static boolean certificateHasPatient(Optional<Certificate> certificate) {
    return certificate.stream()
        .map(
            cert -> cert.certificateMetaData().patient()
        ).anyMatch(Objects::nonNull);
  }

  private static boolean actionEvaluationHasPatient(Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.stream()
        .map(
            ActionEvaluation::patient
        ).anyMatch(Objects::nonNull);
  }
}
