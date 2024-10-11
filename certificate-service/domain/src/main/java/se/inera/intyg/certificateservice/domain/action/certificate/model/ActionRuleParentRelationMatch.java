package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;

public class ActionRuleParentRelationMatch implements ActionRule {

  private final List<RelationType> types;

  public ActionRuleParentRelationMatch(List<RelationType> types) {
    this.types = types;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.filter(
        value -> value.parent() != null && types.contains(value.parent().type())).isPresent();
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Du saknar behörighet för den begärda åtgärden eftersom intyget inte har en förälder med typ: %s"
        .formatted(types);
  }
}
