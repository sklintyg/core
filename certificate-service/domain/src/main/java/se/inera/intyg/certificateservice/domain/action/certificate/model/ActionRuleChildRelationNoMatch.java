package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public class ActionRuleChildRelationNoMatch implements ActionRule {

  private final List<RelationType> relationTypes;
  private final List<Status> allowedStatuses;

  public ActionRuleChildRelationNoMatch(List<RelationType> relationTypes,
      List<Status> allowedStatuses) {
    this.relationTypes = relationTypes;
    this.allowedStatuses = allowedStatuses;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.filter(this::doesntMatchRelationType).isPresent();
  }

  private boolean doesntMatchRelationType(Certificate certificate) {
    return certificate.children().stream()
        .noneMatch(relation ->
            relationTypes.contains(relation.type())
                && !allowedStatuses.contains(relation.certificate().status())
        );
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Du saknar behörighet för den begärda åtgärden eftersom intyget redan har relation med typ: %s"
        .formatted(relationTypes);
  }
}
