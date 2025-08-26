package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
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

class ActionRuleChildRelationMatchTest {

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
    final var actionRuleChildRelation = new ActionRuleChildRelationMatch(
        List.of(RelationType.REPLACE));

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .build()
            )
        )
        .build();

    assertTrue(
        actionRuleChildRelation.evaluate(Optional.of(certificate), Optional.of(ACTION_EVALUATION))
    );
  }

  @Test
  void shallReturnFalseIfRelationTypeMatchesAndIsSigned() {
    final var actionRuleChildRelation = new ActionRuleChildRelationMatch(
        List.of(RelationType.REPLACE));

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .certificate(
                        fk7210CertificateBuilder()
                            .status(Status.SIGNED)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    assertFalse(
        actionRuleChildRelation.evaluate(Optional.of(certificate), Optional.of(ACTION_EVALUATION))
    );
  }

  @Test
  void shallReturnFalseIfRelationTypeDoesntMatch() {
    final var actionRuleChildRelation = new ActionRuleChildRelationMatch(
        List.of(RelationType.RENEW));

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .build()
            )
        )
        .build();

    assertFalse(
        actionRuleChildRelation.evaluate(Optional.of(certificate), Optional.of(ACTION_EVALUATION))
    );
  }

  @Test
  void shallReturnReason() {
    final var actionRuleChildRelation = new ActionRuleChildRelationMatch(
        List.of(RelationType.REPLACE));

    assertEquals(
        "Du saknar behörighet för den begärda åtgärden eftersom intyget saknar utkast med relation med typ: [REPLACE]",
        actionRuleChildRelation.getReasonForPermissionDenied()
    );
  }
}