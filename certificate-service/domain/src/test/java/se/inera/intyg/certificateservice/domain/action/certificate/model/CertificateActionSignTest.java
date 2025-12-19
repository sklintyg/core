package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
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
import java.util.Collections;
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
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityActionsConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessUnitConfiguration;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants;

@ExtendWith(MockitoExtension.class)
class CertificateActionSignTest {

  private static final CertificateType CERTIFICATE_TYPE = new CertificateType("type");
  public static final CertificateVersion VERSION = new CertificateVersion("1.0");
  private static final CertificateModel CERTIFICATE_MODEL = CertificateModel.builder()
      .id(
          CertificateModelId.builder()
              .type(CERTIFICATE_TYPE)
              .version(VERSION)
              .build()
      )
      .activeFrom(LocalDateTime.now().minusDays(1))
      .build();
  private CertificateActionSign certificateActionSign;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SIGN)
          .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
          .build();

  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionSign = (CertificateActionSign) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION);
    certificateBuilder = ag7804CertificateBuilder()
        .certificateModel(CERTIFICATE_MODEL)
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
    assertEquals(CertificateActionType.SIGN, certificateActionSign.getType());
  }

  @Test
  void shallReturnFalseIfCertificateIsEmpty() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var result = certificateActionSign.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));

    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfNotAllowedToSign() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfAllowedToSign() {
    final var certificateActionSignAction = (CertificateActionSign) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionSignAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfSigned() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertFalse(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfDeletedDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DELETED_DRAFT)
        .build();

    assertFalse(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    assertTrue(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class NameTests {

    @Test
    void shallReturnSignNameIfSendAfterSignIsNotPresent() {
      final var certificate = certificateBuilder.certificateModel(
              CertificateModel.builder()
                  .certificateActionSpecifications(
                      List.of(
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SIGN)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      assertEquals("Signera intyget", certificateActionSign.getName(Optional.of(certificate)));
    }

    @Test
    void shallReturnSendAfterSignNameIfSendAfterSignIsPresent() {
      final var certificate = certificateBuilder.certificateModel(
              CertificateModel.builder()
                  .certificateActionSpecifications(
                      List.of(
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      assertEquals("Signera och skicka", certificateActionSign.getName(Optional.of(certificate)));
    }

    @Test
    void shallReturnSendAfterSignNameIfCertificateHasComplementRelationWithSendAfterComplementAction() {
      final var certificate = certificateBuilder.parent(
              Relation.builder()
                  .type(RelationType.COMPLEMENT)
                  .build()
          )
          .certificateModel(
              CertificateModel.builder()
                  .certificateActionSpecifications(
                      List.of(
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SIGN)
                              .build(),
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      assertEquals("Signera och skicka", certificateActionSign.getName(Optional.of(certificate)));
    }
  }

  @Nested
  class DescriptionTests {

    @Test
    void shallReturnSignDescriptionIfSendAfterSignIsNotPresent() {
      final var certificate = certificateBuilder.certificateModel(
              CertificateModel.builder()
                  .certificateActionSpecifications(
                      List.of(
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SIGN)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      assertEquals("Intyget signeras.",
          certificateActionSign.getDescription(Optional.of(certificate)));
    }

    @Test
    void shallReturnSendAfterSignDescriptionIfSendAfterSignIsPresent() {
      final var certificate = certificateBuilder.certificateModel(
              CertificateModel.builder()
                  .certificateActionSpecifications(
                      List.of(
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
                              .build()
                      )
                  )
                  .recipient(
                      new Recipient(
                          new RecipientId("id"),
                          "Försäkringskassan",
                          "LOGICAL_ADDRESS"
                      )
                  )
                  .build()
          )
          .build();
      assertEquals("Intyget skickas direkt till Försäkringskassan.",
          certificateActionSign.getDescription(Optional.of(certificate)));
    }

    @Test
    void shallReturnSendAfterSignDescriptionIfCertificateHasComplementRelationWithSendAfterComplementAction() {
      final var certificate = certificateBuilder.certificateModel(
              CertificateModel.builder()
                  .certificateActionSpecifications(
                      List.of(
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SIGN)
                              .build(),
                          CertificateActionSpecification.builder()
                              .certificateActionType(CertificateActionType.SEND_AFTER_COMPLEMENT)
                              .build()
                      )
                  )
                  .recipient(
                      new Recipient(
                          new RecipientId("id"),
                          "Försäkringskassan",
                          "LOGICAL_ADDRESS"
                      )
                  )
                  .build()
          )
          .parent(
              Relation.builder()
                  .type(RelationType.COMPLEMENT)
                  .build()
          )
          .build();
      assertEquals("Intyget skickas direkt till Försäkringskassan.",
          certificateActionSign.getDescription(Optional.of(certificate)));
    }
  }


  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actualResult = certificateActionSign.reasonNotAllowed(
        Optional.of(certificateBuilder.build()), Optional.empty());

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    final var actualResult = certificateActionSign.reasonNotAllowed(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult.isEmpty());
  }

  @Nested
  class AccessScope {

    private se.inera.intyg.certificateservice.domain.common.model.AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      doReturn(Collections.emptyList()).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);
      certificateActionSign = (CertificateActionSign) certificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION);
      certificateBuilder = MedicalCertificate.builder()
          .certificateModel(CERTIFICATE_MODEL)
          .status(Status.DRAFT)
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class UnitAccessEvaluationTests {

    @BeforeEach
    void setUp() {
      certificateActionSign = (CertificateActionSign) certificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION);
      certificateBuilder = MedicalCertificate.builder()
          .certificateModel(CERTIFICATE_MODEL)
          .status(Status.DRAFT)
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
    void shallReturnTrueIfUnitHasAccess() {
      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(CERTIFICATE_TYPE.type())
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type("allow")
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .build()
                  )
              )
              .build()
      );
      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);
      final var certificateActionSign = (CertificateActionSign) certificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION
      );

      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder.build();

      assertTrue(
          certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }

    @Test
    void shallReturnFalseIfUnitDontHaveAccess() {
      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(CERTIFICATE_TYPE.type())
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type("block")
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .build()
                  )
              )
              .build()
      );
      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);
      final var certificateActionSign = (CertificateActionSign) certificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION
      );

      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder.build();

      assertFalse(
          certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
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
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserHasAgreement() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
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
          certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
          certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
          certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
          certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }
}