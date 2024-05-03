package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public class ActionRuleChildRelation implements ActionRule {

  private final List<RelationType> relationTypes;

  public ActionRuleChildRelation(List<RelationType> relationTypes) {
    this.relationTypes = relationTypes;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return certificate.filter(this::doesntMatchRelationType).isPresent();
  }

  private boolean doesntMatchRelationType(Certificate certificate) {
    return certificate.children().stream()
        .noneMatch(relation ->
            relationTypes.contains(relation.type()) && !Status.REVOKED.equals(relation.status())
        );
  }

  @Override
  public String getReasonForPermissionDenied() {
    return null;
  }
}
