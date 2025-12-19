package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public class ActionRuleStatus implements ActionRule {

  private final List<Status> status;

  public ActionRuleStatus(List<Status> status) {
    this.status = status;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.filter(value -> status.contains(value.status())).isPresent();
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "För att genomföra den begärda åtgärden behöver intygets status vara %s".formatted(
        status.toString()
    );
  }
}