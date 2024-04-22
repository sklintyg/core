package se.inera.intyg.certificateservice.domain.action.model;

import static se.inera.intyg.certificateservice.domain.common.model.Role.CARE_ADMIN;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

public class ActionRuleProtectedPerson implements ActionRule {

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation) {
    return ifPatientIsProtectedUserMustNotBeCareAdmin(actionEvaluation, actionEvaluation.patient());
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return certificate
        .filter(value ->
            ifPatientIsProtectedUserMustNotBeCareAdmin(actionEvaluation,
                value.certificateMetaData().patient()
            )
        )
        .isPresent();
  }

  @Override
  public String getErrorMessage() {
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
