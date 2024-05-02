package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;

class ActionRuleWithinAccessScopeTest {

  private ActionRuleWithinAccessScope actionRuleWithinAccessScope;
  private CertificateBuilder certificateBuilder;


  @BeforeEach
  void setUp() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_PROVIDER);

    certificateBuilder = Certificate.builder()
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
  void shallReturnTrueIfRuleAccessScopeIsWithinCareUnitAndUserAccessScopeIsWithinCareUnit() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(AccessScope.WITHIN_CARE_UNIT)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }


  @Test
  void shallReturnTrueIfRuleAccessScopeIsWithinCareProviderAndUserAccessScopeIsWithinCareProvider() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_PROVIDER);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(AccessScope.WITHIN_CARE_PROVIDER)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRuleAccessScopeIsWithinCareProviderAndUserAccessScopeIsWithinCareUnit() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_PROVIDER);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(AccessScope.WITHIN_CARE_UNIT)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfRuleAccessScopeIsAllCareProvidersAndUserAccessScopeIsAllCareProviders() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.ALL_CARE_PROVIDERS);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(AccessScope.ALL_CARE_PROVIDERS)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRuleAccessScopeIsAllCareProvidersAndUserAccessScopeIsWithinCareProvider() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.ALL_CARE_PROVIDERS);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(AccessScope.WITHIN_CARE_PROVIDER)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfRuleAccessScopeIsWithinCareUnitAndUserAccessScopeIsNull() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_UNIT);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(null)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRuleAccessScopeIsWithinCareProviderAndUserAccessScopeIsNull() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.WITHIN_CARE_PROVIDER);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(null)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRuleAccessScopeIsAllCareProvidersAndUserAccessScopeIsNull() {
    actionRuleWithinAccessScope = new ActionRuleWithinAccessScope(AccessScope.ALL_CARE_PROVIDERS);

    final var actionEvaluation = ActionEvaluation.builder()
        .user(ajlaDoctorBuilder()
            .accessScope(null)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

//  @Test
//  void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
//    final var actionEvaluation = actionEvaluationBuilder
//        .user(ajlaDoctorBuilder()
//            .accessScope(AccessScope.WITHIN_CARE_PROVIDER)
//            .build())
//        .build();
//
//    final var certificate = certificateBuilder.build();
//
//    assertTrue(
//        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
//        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
//    );
//  }
//
//  @Test
//  void shallReturnTrueIfCareUnitMatchesSubUnit() {
//    final var actionEvaluation = actionEvaluationBuilder
//        .subUnit(
//            SubUnit.builder()
//                .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
//                .build()
//        )
//        .build();
//
//    final var certificate = certificateBuilder.build();
//
//    assertTrue(
//        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
//        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
//    );
//  }
//
//  @Test
//  void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
//    final var actionEvaluation = actionEvaluationBuilder
//        .subUnit(
//            SubUnit.builder()
//                .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
//                .build()
//        )
//        .build();
//
//    final var certificate = certificateBuilder.build();
//
//    assertFalse(
//        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
//        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
//    );
//  }
//
//  @Test
//  void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
//    final var actionEvaluation = actionEvaluationBuilder
//        .subUnit(
//            SubUnit.builder()
//                .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
//                .build()
//        )
//        .build();
//
//    final var certificate = certificateBuilder.build();
//
//    assertFalse(
//        actionRuleWithinAccessScope.evaluate(Optional.of(certificate), actionEvaluation),
//        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
//    );
//  }

}