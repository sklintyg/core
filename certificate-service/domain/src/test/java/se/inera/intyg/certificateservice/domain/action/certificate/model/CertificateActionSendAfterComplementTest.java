package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.alveReactAlfredssonBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AGREEMENT_FALSE;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;

@ExtendWith(MockitoExtension.class)
class CertificateActionSendAfterComplementTest {

  private CertificateActionSendAfterComplement certificateActionSendAfterComplement;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
          .build();
  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionSendAfterComplement = (CertificateActionSendAfterComplement) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    certificateBuilder = Certificate.builder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ATHENA_REACT_ANDERSSON)
                .build()
        );

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);
  }

  @Test
  void shallReturnTypeFromSpecification() {
    assertEquals(CertificateActionType.SEND_AFTER_COMPLEMENT,
        certificateActionSendAfterComplement.getType());
  }

  @Test
  void shallReturnTrueIfCertificateIsSignedAndHasParentOfTypeComplement() {
    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .parent(
            Relation.builder()
                .type(RelationType.COMPLEMENT)
                .build()
        )
        .build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertTrue(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfCertificateIsSignedAndHasParentOfTypeNotComplement() {
    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .parent(
            Relation.builder()
                .type(RelationType.REPLACE)
                .build()
        )
        .build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertFalse(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }


  @Test
  void shallReturnFalseIfCertificateIsNotSigned() {
    final var certificate = certificateBuilder.status(Status.DRAFT).build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertFalse(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfCertificateAlreadyIsSent() {
    final var certificate = certificateBuilder.sent(Sent.builder().build()).build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertFalse(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ANONYMA_REACT_ATTILA)
                .build()
        )
        .build();

    assertFalse(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserIsDoctorAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .parent(
            Relation.builder()
                .type(RelationType.COMPLEMENT)
                .build()
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ANONYMA_REACT_ATTILA)
                .build()
        )
        .build();

    assertTrue(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfUserIsDoctorAndPatientIsTestIndicated() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(
                    alveReactAlfredssonBuilder().testIndicated(new TestIndicated(true)).build())
                .build()
        )
        .build();

    assertFalse(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfUserMissingAgreement() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            ajlaDoctorBuilder()
                .agreement(AGREEMENT_FALSE)
                .build()
        )
        .build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .parent(
            Relation.builder()
                .type(RelationType.COMPLEMENT)
                .build()
        )
        .build();

    assertFalse(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserHasAgreement() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .parent(
            Relation.builder()
                .type(RelationType.COMPLEMENT)
                .build()
        )
        .build();

    assertTrue(
        certificateActionSendAfterComplement.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }
}