package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public class ActionRuleChildRelationNotSigned implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate
        .map(Certificate::children)
        .filter(this::notSigned)
        .isPresent();
  }

  private boolean notSigned(List<Relation> relations) {
    return relations.stream()
        .noneMatch(relation -> Status.SIGNED.equals(relation.certificate().status()));
  }
}
