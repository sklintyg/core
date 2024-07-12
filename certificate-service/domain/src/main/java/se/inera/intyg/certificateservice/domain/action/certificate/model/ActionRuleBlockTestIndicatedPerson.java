package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

@Builder
@Getter(AccessLevel.NONE)
public class ActionRuleBlockTestIndicatedPerson implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    if (certificate.isEmpty()) {
      return evaluate(actionEvaluation);
    }

    return actionEvaluation.filter(
        evaluation ->
            certificate.filter(
                    value -> isPatientNotTestIndicated(value.certificateMetaData().patient())
                )
                .isPresent()
    ).isPresent();
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Åtgärden kan inte utföras för testpersoner.";
  }

  private static boolean evaluate(Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.filter(
            evaluation -> isPatientNotTestIndicated(evaluation.patient())
        )
        .isPresent();
  }

  private static boolean isPatientNotTestIndicated(Patient patient) {
    return !patient.testIndicated().value();
  }
}