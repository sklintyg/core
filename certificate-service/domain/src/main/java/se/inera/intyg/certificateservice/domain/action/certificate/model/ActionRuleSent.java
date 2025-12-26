package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleSent implements ActionRule {

  private final boolean sent;

  public ActionRuleSent(boolean sent) {
    this.sent = sent;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.filter(value -> sent == (value.sent() != null)).isPresent();
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "För att genomföra den begärda åtgärden behöver intyget vara skickat till en intygsmottagare.";
  }
}