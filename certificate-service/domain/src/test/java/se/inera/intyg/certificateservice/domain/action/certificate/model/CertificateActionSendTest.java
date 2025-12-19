package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.BETA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ag7804CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.ag7804certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7804certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.BETA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AGREEMENT_FALSE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionContentProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityActionsConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants;

@ExtendWith(MockitoExtension.class)
class CertificateActionSendTest {


  @Mock
  private CertificateActionContentProvider certificateActionContentProvider;
  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  private CertificateActionSend certificateActionSend;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SEND)
          .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
          .build();

  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionSend = (CertificateActionSend) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    certificateBuilder = ag7804CertificateBuilder()
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
    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);
  }

  @Test
  void shallReturnTypeFromSpecification() {
    assertEquals(CertificateActionType.SEND, certificateActionSend.getType());
  }

  @Test
  void shallReturnFalseIfCertificateIsEmpty() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var result = certificateActionSend.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));

    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfNotAllowedToSend() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfPatientIsTestIndicated() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(
                    Patient.builder()
                        .testIndicated(new TestIndicated(true))
                        .protectedPerson(new ProtectedPerson(false))
                        .build()
                )
                .build()
        ).build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfAllowedToSend() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfSigned() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfDeletedDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DELETED_DRAFT)
        .build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRevoked() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.REVOKED)
        .build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfNotSent() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfSent() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .sent(TestDataCertificate.SENT)
        .build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }


  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actualResult = certificateActionSend.reasonNotAllowed(
        Optional.of(certificateBuilder.build()), Optional.empty());

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var actualResult = certificateActionSend.reasonNotAllowed(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult.isEmpty());
  }

  @Nested
  class AccessScope {

    private se.inera.intyg.certificateservice.domain.common.model.AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      certificateActionSend = (CertificateActionSend) certificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION);
      certificateBuilder = ag7804CertificateBuilder()
          .status(Status.SIGNED)
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .patient(ATHENA_REACT_ANDERSSON)
                  .build()
          );
      actionEvaluationBuilder = ActionEvaluation.builder()
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .patient(ATHENA_REACT_ANDERSSON)
          .careProvider(ALFA_REGIONEN)
          .careUnit(ALFA_MEDICINCENTRUM);
    }

    @Nested
    class UserAccessScopeWithinCareUnit {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_UNIT;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserAccessScopeWithinCareProvider {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_PROVIDER;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(ALFA_VARDCENTRAL)
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserAccessScopeAllCareProviders {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.ALL_CARE_PROVIDERS;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(ALFA_VARDCENTRAL)
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(BETA_VARDCENTRAL)
            .subUnit(BETA_HUDMOTTAGNINGEN)
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }

  @Test
  void shallReturnFalseIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder
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
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserIsDoctorAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
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
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserHasAgreement() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class RecipientTests {

    @Nested
    class RecipientForsakringskassanTests {

      @Test
      void shallReturnName() {
        final var certificate = certificateBuilder
            .certificateModel(
                CertificateModel.builder()
                    .recipient(
                        new Recipient(
                            new RecipientId("id"),
                            "Försäkringskassan",
                            "logicalAddress"
                        )
                    )
                    .build()
            )
            .build();
        assertEquals("Skicka till Försäkringskassan",
            certificateActionSend.getName(Optional.of(certificate)));
      }

      @Test
      void shallReturnDescription() {
        final var certificate = certificateBuilder
            .certificateModel(
                CertificateModel.builder()
                    .recipient(
                        new Recipient(
                            new RecipientId("id"),
                            "Försäkringskassan",
                            "logicalAddress"
                        )
                    )
                    .build()
            )
            .build();
        assertEquals(
            "Öppnar ett fönster där du kan välja att skicka intyget till Försäkringskassan.",
            certificateActionSend.getDescription(Optional.of(certificate)));
      }

      @Test
      void shallReturnBody() {
        final var actionEvaluation = actionEvaluationBuilder.build();
        final var certificate = certificateBuilder
            .certificateModel(
                CertificateModel.builder()
                    .recipient(
                        new Recipient(
                            new RecipientId("id"),
                            "Försäkringskassan",
                            "logicalAddress"
                        )
                    )
                    .build()
            )
            .build();
        assertEquals(
            "<p>Om du går vidare kommer intyget skickas direkt till Försäkringskassans system vilket ska göras i samråd med patienten.</p>",
            certificateActionSend.getBody(Optional.of(certificate),
                Optional.of(actionEvaluation))
        );
      }
    }

    @Nested
    class RecipientTranssportstyrelsenTests {

      @Test
      void shallReturnName() {
        final var certificate = certificateBuilder
            .certificateModel(
                CertificateModel.builder()
                    .recipient(
                        new Recipient(
                            new RecipientId("id"),
                            "Transportstyrelsen",
                            "logicalAddress"
                        )
                    )
                    .build()
            )
            .build();
        assertEquals("Skicka till Transportstyrelsen",
            certificateActionSend.getName(Optional.of(certificate)));
      }

      @Test
      void shallReturnDescription() {
        final var certificate = certificateBuilder
            .certificateModel(
                CertificateModel.builder()
                    .recipient(
                        new Recipient(
                            new RecipientId("id"),
                            "Transportstyrelsen",
                            "logicalAddress"
                        )
                    )
                    .build()
            )
            .build();
        assertEquals(
            "Öppnar ett fönster där du kan välja att skicka intyget till Transportstyrelsen.",
            certificateActionSend.getDescription(Optional.of(certificate)));
      }

      @Test
      void shallReturnBody() {
        final var actionEvaluation = actionEvaluationBuilder.build();
        final var certificate = certificateBuilder
            .certificateModel(
                CertificateModel.builder()
                    .recipient(
                        new Recipient(
                            new RecipientId("id"),
                            "Transportstyrelsen",
                            "logicalAddress"
                        )
                    )
                    .build()
            )
            .build();

        assertEquals(
            "<p>Om du går vidare kommer intyget skickas direkt till Transportstyrelsens system vilket ska göras i samråd med patienten.</p>",
            certificateActionSend.getBody(Optional.of(certificate),
                Optional.of(actionEvaluation))
        );
      }
    }
  }

  @Nested
  class SentContentProviderTests {

    @BeforeEach
    void setUp() {
      certificateActionSend = (CertificateActionSend) certificateActionFactory.create(
          CertificateActionSpecification.builder()
              .certificateActionType(CertificateActionType.SEND)
              .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
              .contentProvider(certificateActionContentProvider)
              .build()
      );
    }

    @Test
    void shallReturnBodyFromProvider() {
      final var expectedBody = "expectedBody";
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .certificateModel(
              CertificateModel.builder()
                  .recipient(
                      new Recipient(
                          new RecipientId("id"),
                          "Transportstyrelsen",
                          "logicalAddress"
                      )
                  )
                  .build()
          )
          .build();

      when(certificateActionContentProvider.body(certificate)).thenReturn(expectedBody);

      assertEquals(
          expectedBody,
          certificateActionSend.getBody(Optional.of(certificate),
              Optional.of(actionEvaluation))
      );
    }
  }

  @Nested
  class EvaluateLimitedFunctionalityTests {

    @Test
    void shallReturnFalseIfCertificateIsNotMajorVersionWithLimitedFunctionalityConfiguration() {
      final var inactiveConfigurations =
          LimitedCertificateFunctionalityConfiguration.builder()
              .certificateType("type")
              .version(List.of("1.0"))
              .configuration(
                  LimitedCertificateFunctionalityActionsConfiguration.builder()
                      .build()
              )
              .build();

      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var inactiveModel = ag7804certificateModelBuilder()
          .id(
              CertificateModelId.builder()
                  .type(TestDataCertificateModelConstants.AG7804_TYPE)
                  .version(new CertificateVersion("2"))
                  .build()
          )
          .build();

      final var activeModel = ag7804certificateModelBuilder()
          .id(
              CertificateModelId.builder()
                  .type(TestDataCertificateModelConstants.AG7804_TYPE)
                  .version(new CertificateVersion("3"))
                  .build()
          )
          .build();

      final var certificateModel = inactiveModel.withVersions(List.of(inactiveModel, activeModel));
      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      when(
          certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
              certificate.certificateModel().id()))
          .thenReturn(inactiveConfigurations);

      assertFalse(
          certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }

    @Test
    void shallReturnTrueIfCertificateIsNotMajorVersionWithoutLimitedFunctionalityConfiguration() {
      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var inactiveModel = ag7804certificateModelBuilder()
          .id(
              CertificateModelId.builder()
                  .type(TestDataCertificateModelConstants.AG7804_TYPE)
                  .version(new CertificateVersion("2"))
                  .build()
          )
          .build();

      final var activeModel = ag7804certificateModelBuilder()
          .id(
              CertificateModelId.builder()
                  .type(TestDataCertificateModelConstants.AG7804_TYPE)
                  .version(new CertificateVersion("3"))
                  .build()
          )
          .build();

      final var certificateModel = inactiveModel.withVersions(List.of(inactiveModel, activeModel));
      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      when(
          certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
              certificate.certificateModel().id()))
          .thenReturn(null);

      assertTrue(
          certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }

  @Nested
  class ActiveCertificateTests {

    @Test
    void shouldReturnFalseIfCertificateIsInactive() {
      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder
          .certificateModel(
              fk7804certificateModelBuilder()
                  .activeFrom(LocalDateTime.now().plusDays(1))
                  .build()
          )
          .build();

      assertFalse(
          certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }

    @Test
    void shouldReturnTrueIfCertificateIsActive() {
      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder
          .certificateModel(
              fk7804certificateModelBuilder()
                  .activeFrom(LocalDateTime.now().minusDays(1))
                  .build()
          )
          .build();

      assertTrue(
          certificateActionSend.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }
}