package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.RELATION_REPLACE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.relationReplaceBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class ActionRuleChildRelationTest {

  private Certificate.CertificateBuilder certificateBuilder;

  @BeforeEach
  void setUp() {
    certificateBuilder = Certificate.builder()
        .status(Status.SIGNED)
        .sent(null)
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ATHENA_REACT_ANDERSSON)
                .build()
        )
        .children(
            List.of(RELATION_REPLACE)
        );
  }

  @Test
  void shallReturnFalseIfRelationTypeMatches() {
    final var actionRuleChildRelation = new ActionRuleChildRelation(List.of(RelationType.REPLACE));

    final var certificate = certificateBuilder.build();

    assertFalse(
        actionRuleChildRelation.evaluate(Optional.of(certificate), ACTION_EVALUATION)
    );
  }

  @Test
  void shallReturnTrueIfRelationTypeDoesntMatch() {
    final var actionRuleChildRelation = new ActionRuleChildRelation(List.of(RelationType.RENEW));

    final var certificate = certificateBuilder.build();

    assertTrue(
        actionRuleChildRelation.evaluate(Optional.of(certificate), ACTION_EVALUATION)
    );
  }

  @Test
  void shallReturnTrueIfRelationTypeMatchesButIsRevoked() {
    final var actionRuleChildRelation = new ActionRuleChildRelation(List.of(RelationType.REPLACE));

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .status(Status.REVOKED)
                    .build()
            )
        )
        .build();

    assertTrue(
        actionRuleChildRelation.evaluate(Optional.of(certificate), ACTION_EVALUATION)
    );
  }
}