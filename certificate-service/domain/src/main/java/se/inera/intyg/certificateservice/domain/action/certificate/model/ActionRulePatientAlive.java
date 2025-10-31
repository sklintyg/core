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
  public String getReasonForPermissionDenied(Optional<Certificate> certificate) {
    if (certificateHasPatient(certificate) && certificate.isPresent()) {
      return String.format("Cannot issue intyg type %s for deceased patient %s",
          certificate.get().certificateModel().type().displayName(),
          certificate.get().certificateMetaData().patient().id().idWithDash());
    }

    return "Du saknar behörighet för den begärda åtgärden."
        + " Det krävs särskilda rättigheter eller en specifik befattning"
        + " för att hantera avlidna patienter.";
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
