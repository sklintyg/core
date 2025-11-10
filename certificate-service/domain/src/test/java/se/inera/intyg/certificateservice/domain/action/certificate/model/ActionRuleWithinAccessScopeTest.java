package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;

class ActionRuleWithinAccessScopeTest {

  private ActionRuleWithinAccessScope actionRuleWithinAccessScope;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  private AccessScope userAccessScope;

  @BeforeEach
  void setUp() {
    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);

    certificateBuilder = MedicalCertificate.builder()
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
  void shallValidateAsWithinCareUnitIfUserMissingAccessScopeAndEvaluateToTrue() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(
        AccessScope.WITHIN_CARE_PROVIDER);

    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(SubUnit.builder()
            .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
            .build())
        .user(ajlaDoctorBuilder()
            .accessScope(null)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallValidateAsWithinCareUnitIfUserMissingAccessScopeAndEvaluateToFalse() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(
        AccessScope.WITHIN_CARE_PROVIDER);

    final var actionEvaluation = actionEvaluationBuilder
        .careUnit(ALFA_VARDCENTRAL)
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                .build()
        )
        .user(ajlaDoctorBuilder()
            .accessScope(userAccessScope)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class RuleWithinCareUnit {

    @BeforeEach
    void setUp() {
      actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(
          AccessScope.WITHIN_CARE_UNIT);

    }

    @Nested
    class UserScopeWithinCareUnit {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.WITHIN_CARE_UNIT;
      }

      @Test
      void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(SubUnit.builder()
                .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
                .build())
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserScopeWithinCareProvider {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.WITHIN_CARE_PROVIDER;
      }

      @Test
      void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(SubUnit.builder()
                .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
                .build())
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(ALFA_VARDCENTRAL)
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(ALFA_VARDCENTRAL)
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserScopeAllCareProviders {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.ALL_CARE_PROVIDERS;
      }

      @Test
      void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(SubUnit.builder()
                .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
                .build())
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(ALFA_VARDCENTRAL)
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(ALFA_VARDCENTRAL)
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }

  @Nested
  class RuleWithinCareProvider {

    @BeforeEach
    void setUp() {
      actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(
          AccessScope.WITHIN_CARE_PROVIDER);

    }

    @Nested
    class UserScopeWithinCareUnit {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.WITHIN_CARE_UNIT;
      }

      @Test
      void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(SubUnit.builder()
                .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
                .build())
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserScopeWithinCareProvider {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.WITHIN_CARE_PROVIDER;
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitDoesNotMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareProviderDoesNotMatchCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserScopeAllCareProviders {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.ALL_CARE_PROVIDERS;
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitDoesNotMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareProviderDoesNotMatchCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }

  @Nested
  class RuleAllCareProviders {

    @BeforeEach
    void setUp() {
      actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(
          AccessScope.ALL_CARE_PROVIDERS);

    }

    @Nested
    class UserScopeWithinCareUnit {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.WITHIN_CARE_UNIT;
      }

      @Test
      void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(SubUnit.builder()
                .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
                .build())
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                    .build()
            )
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserScopeWithinCareProvider {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.WITHIN_CARE_PROVIDER;
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitDoesNotMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfCareProviderDoesNotMatchCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserScopeAllCareProviders {

      @BeforeEach
      void setUp() {
        userAccessScope = AccessScope.ALL_CARE_PROVIDERS;
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitMatchesSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareProviderMatchesCareProviderAndSubUnitDoesNotMatchSubUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .careProvider(ALFA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfCareProviderDoesNotMatchCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            actionRuleWithinAccessScope.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }
}