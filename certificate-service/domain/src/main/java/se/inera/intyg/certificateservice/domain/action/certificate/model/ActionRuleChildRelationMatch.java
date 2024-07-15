package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public class ActionRuleChildRelationMatch implements ActionRule {

  private final List<RelationType> relationTypes;

  public ActionRuleChildRelationMatch(List<RelationType> relationTypes) {
    this.relationTypes = relationTypes;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.filter(this::matchRelationType).isPresent();
  }

  private boolean matchRelationType(Certificate certificate) {
    return certificate.children().stream()
        .anyMatch(relation ->
            relationTypes.contains(relation.type())
                && Status.DRAFT.equals(relation.certificate().status())
        );
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Du saknar behörighet för den begärda åtgärden eftersom intyget saknar utkast med relation med typ: %s"
        .formatted(relationTypes);
  }
}
