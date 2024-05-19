package se.inera.intyg.certificateservice.domain.action.model;

import static se.inera.intyg.certificateservice.domain.common.model.Role.CARE_ADMIN;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

public class ActionRuleProtectedPerson implements ActionRule {

  @Override
  public boolean evaluate(Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.filter(
            evaluation ->
                ifPatientIsProtectedUserMustNotBeCareAdmin(
                    evaluation,
                    evaluation.patient()
                )
        )
        .isPresent();
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.filter(
        evaluation ->
            certificate.filter(value ->
                    ifPatientIsProtectedUserMustNotBeCareAdmin(evaluation,
                        value.certificateMetaData().patient()
                    )
                )
                .isPresent()
    ).isPresent();
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Du saknar behörighet för den begärda åtgärden."
        + " Det krävs särskilda rättigheter eller en specifik befattning"
        + " för att hantera patienter med skyddade personuppgifter.";
  }

  private static boolean ifPatientIsProtectedUserMustNotBeCareAdmin(
      ActionEvaluation actionEvaluation, Patient patient) {
    return !patient.protectedPerson().value()
        || !CARE_ADMIN.equals(actionEvaluation.user().role());
  }
}
