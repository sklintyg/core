package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.BETA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.relationReplaceBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.BETA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ANNA_SJUKSKOTERKSA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.BERTIL_BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.alvaVardadministratorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AGREEMENT_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALLOW_COPY_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation.ActionEvaluationBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@ExtendWith(MockitoExtension.class)
class CertificateActionForwardCertificateFromListTest {

  private CertificateActionForwardCertificateFromList certificateActionForwardCertificateFromList;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST)
          .build();
  private CertificateBuilder certificateBuilder;
  private ActionEvaluationBuilder actionEvaluationBuilder;
  
  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionForwardCertificateFromList = (CertificateActionForwardCertificateFromList) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);

    certificateBuilder = Certificate.builder()
        .status(Status.DRAFT)
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
  void shallReturnName() {
    assertEquals("Vidarebefordra utkast",
        certificateActionForwardCertificateFromList.getName(Optional.empty()));
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST,
        certificateActionForwardCertificateFromList.getType());
  }

  @Test
  void shallReturnDescription() {
    assertEquals(
        "Skapar ett e-postmeddelande i din e-postklient med en direktlänk till utkastet.",
        certificateActionForwardCertificateFromList.getDescription(Optional.empty()));
  }

  @Test
  void shallReturnFalseIfUserIsBlocked() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(
            alvaVardadministratorBuilder()
                .blocked(BLOCKED_TRUE)
                .build()
        )
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .build();

    final var certificate = certificateBuilder.build();
    final var actualResult = certificateActionForwardCertificateFromList.evaluate(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfUserIsNotBlocked() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder.build();
    final var actualResult = certificateActionForwardCertificateFromList.evaluate(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult);
  }

  @Test
  void shallReturnFalseIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .build();

    final var certificate = certificateBuilder
        .certificateMetaData(
            CertificateMetaData.builder()
                .patient(ANONYMA_REACT_ATTILA)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .build()
        )
        .build();
    final var actualResult = certificateActionForwardCertificateFromList.evaluate(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertFalse(actualResult);
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .build();

    final var certificate = certificateBuilder.build();
    final var actualResult = certificateActionForwardCertificateFromList.evaluate(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult);
  }

  @Test
  void shallReturnEnabledTrue() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .build();

    final var certificate = certificateBuilder.build();
    final var actualResult = certificateActionForwardCertificateFromList.isEnabled(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult);
  }

  @Test
  void shallReturnFalseIfUserHasAllowCopyFalse() {
    final var actionEvaluation = actionEvaluationBuilder()
        .user(
            alvaVardadministratorBuilder()
                .allowCopy(ALLOW_COPY_FALSE)
                .build()
        )
        .build();
    final var certificate = certificateBuilder.build();
    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(Optional.empty(), actionEvaluation)
    );
  }

  @Test
  void shallReturnTrueIfUserHasAllowCopyTrue() {
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ALVA_VARDADMINISTRATOR)
        .build();
    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(Optional.empty(), actionEvaluation)
    );
  }

  @Nested
  class AccessScope {

    private se.inera.intyg.certificateservice.domain.common.model.AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      certificateBuilder = Certificate.builder()
          .status(Status.DRAFT)
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .patient(ATHENA_REACT_ANDERSSON)
                  .build()
          )
          .children(
              List.of(
                  relationReplaceBuilder()
                      .build()
              )
          );
    }

    @Nested
    class UserAccessScopeWithinCareUnit {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_UNIT;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder()
            .user(alvaVardadministratorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder()
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(alvaVardadministratorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
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
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder()
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(alvaVardadministratorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
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
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder()
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(alvaVardadministratorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder()
            .careUnit(BETA_VARDCENTRAL)
            .subUnit(BETA_HUDMOTTAGNINGEN)
            .careProvider(BETA_REGIONEN)
            .user(alvaVardadministratorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }

  @Test
  void shallReturnFalseIfCertificateIsSigned() {
    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();
    final var actionEvaluation = actionEvaluationBuilder().build();
    assertFalse(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfCertificateIsDraft() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfUserIsDoctor() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(AJLA_DOKTOR)
        .build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfUserIsCareAdmin() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfUserIsNurse() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ANNA_SJUKSKOTERKSA)
        .build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfUserIsMidWife() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(BERTIL_BARNMORSKA)
        .build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfUserAccessScopeIsWithinCareUnit() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfUserAccessScopeIsNotWithinCareUnit() {
    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder()
        .user(alvaVardadministratorBuilder()
            .accessScope(
                se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_PROVIDER)
            .build())
        .build();

    assertFalse(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)));
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
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserHasAgreement() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionForwardCertificateFromList.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }
}
