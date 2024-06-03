package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class ActionRuleChildRelationNotSignedTest {

  private ActionRuleChildRelationNotSigned actionRuleChildRelationNotSigned;

  @BeforeEach
  void setUp() {
    actionRuleChildRelationNotSigned = new ActionRuleChildRelationNotSigned();
  }

  @Test
  void shallReturnTrueIfNoChildrenHasStatusSigned() {
    final var certificate = Certificate.builder()
        .children(
            List.of(
                getRelation(Status.DRAFT),
                getRelation(Status.REVOKED),
                getRelation(Status.LOCKED_DRAFT)
            )
        )
        .build();
    assertTrue(
        actionRuleChildRelationNotSigned.evaluate(Optional.of(certificate), Optional.empty()));
  }

  @Test
  void shallReturnFalseIfChildHasStatusSigned() {
    final var certificate = Certificate.builder()
        .children(
            List.of(
                getRelation(Status.DRAFT),
                getRelation(Status.REVOKED),
                getRelation(Status.SIGNED)
            )
        )
        .build();
    assertFalse(
        actionRuleChildRelationNotSigned.evaluate(Optional.of(certificate), Optional.empty()));
  }

  @Test
  void shallReturnTrueIfNoChildren() {
    final var certificate = Certificate.builder().build();
    assertTrue(
        actionRuleChildRelationNotSigned.evaluate(Optional.of(certificate), Optional.empty()));
  }

  private static Relation getRelation(Status draft) {
    return Relation.builder()
        .certificate(
            Certificate.builder()
                .status(draft)
                .build()
        )
        .build();
  }
}
