package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.relationReplaceBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class ActionRuleParentRelationMatchTest {

  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;

  @BeforeEach
  void setUp() {
    certificateBuilder = MedicalCertificate.builder()
        .status(Status.SIGNED)
        .sent(null)
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ATHENA_REACT_ANDERSSON)
                .build()
        );
  }

  @Test
  void shallReturnTrueIfRelationTypeMatchesAndIsDraft() {
    final var actionRuleParentRelation = new ActionRuleParentRelationMatch(
        List.of(RelationType.REPLACE));

    final var certificate = certificateBuilder
        .parent(
            relationReplaceBuilder()
                .build()
        )
        .build();

    assertTrue(
        actionRuleParentRelation.evaluate(Optional.of(certificate), Optional.of(ACTION_EVALUATION))
    );
  }

  @Test
  void shallReturnFalseIfRelationTypeDoesntMatch() {
    final var actionRuleParentRelation = new ActionRuleParentRelationMatch(
        List.of(RelationType.RENEW));

    final var certificate = certificateBuilder
        .parent(
            relationReplaceBuilder()
                .build()
        )
        .build();

    assertFalse(
        actionRuleParentRelation.evaluate(Optional.of(certificate), Optional.of(ACTION_EVALUATION))
    );
  }

  @Test
  void shallReturnReason() {
    final var actionRuleParentRelation = new ActionRuleParentRelationMatch(
        List.of(RelationType.REPLACE));

    assertEquals(
        "Du saknar behörighet för den begärda åtgärden eftersom intyget inte har en förälder med typ: [REPLACE]",
        actionRuleParentRelation.getReasonForPermissionDenied()
    );
  }
}