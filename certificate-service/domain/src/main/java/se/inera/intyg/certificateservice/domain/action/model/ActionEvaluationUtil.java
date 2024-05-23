package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;

public class ActionEvaluationUtil {

  private ActionEvaluationUtil() {
    throw new IllegalStateException("Utility class");
  }


  static boolean missingPatientOrUser(Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.map(
            evaluation -> evaluation.patient() == null || evaluation.user() == null
        )
        .orElse(true);
  }
}
