package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_MEDICINSKT_CENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.BETA_HUDMOTTAGNINGEN;

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
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessUnitConfiguration;

@ExtendWith(MockitoExtension.class)
class ActionRuleCertificateTypeActiveForUnitTest {

  private static final String FK_3226_TYPE = "fk3226";
  private static final CertificateType CERTIFICATE_TYPE = new CertificateType(FK_3226_TYPE);
  private static final String ALLOW = "allow";
  private static final String BLOCK = "block";
  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  private ActionRuleCertificateTypeActiveForUnit actionRuleCertificateTypeActiveForUnit;
  private Certificate certificate;
  private ActionEvaluation actionEvaluation;

  @BeforeEach
  void setUp() {
    certificate = MedicalCertificate
        .builder()
        .certificateModel(
            CertificateModel.builder()
                .id(
                    CertificateModelId.builder()
                        .type(CERTIFICATE_TYPE)
                        .build()
                )
                .build()
        )
        .build();

    actionEvaluation = ActionEvaluation.builder()
        .subUnit(ALFA_MEDICINSKT_CENTRUM)
        .careUnit(ALFA_VARDCENTRAL)
        .careProvider(ALFA_REGIONEN)
        .build();
  }

  @Test
  void shallReturnFalseIfCertificateIsMissing() {
    final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));
    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfActionEvaluationIsMissing() {
    final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
        Optional.empty());
    assertFalse(result);
  }

  @Test
  void shallReturnTrueIfNoConfigurationForTypeExists() {
    doReturn(Collections.emptyList()).when(certificateActionConfigurationRepository)
        .findAccessConfiguration(CERTIFICATE_TYPE);
    final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(result);
  }

  @Nested
  class AllowTest {

    @Test
    void shallReturnTrueIfTypeIsAllowAndSubUnitIsPresentInIssuingUnitConfigurationWithinDateTime() {
      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsAllowAndSubUnitIsPresentInIssuingUnitConfigurationButFromDateTimeIsNull() {
      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }


    @Test
    void shallReturnTrueIfTypeIsAllowAndCareUnitIsPresentInCareUnitConfigurationWithinDateTime() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsAllowAndCareUnitIsPresentInCareUnitConfigurationButFromDateTimeIsNull() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsAllowAndCareProviderIsPresentInCareProviderConfigurationWithinDateTime() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsAllowAndCareProviderIsPresentInCareProviderConfigurationButFromDateTimeIsNull() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsAllowAndFromDateIsBeforeTodayWithToDateAsNullWithMatchingUnit() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfFromDateAndToDateIsMissingSinceRuleIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsAllowAndUnitNotFoundInConfigurationThatIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().plusDays(1))
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnFalseIfTypeIsAllowAndUnitNotFoundInConfigurationThatIsActive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }
  }

  @Nested
  class BlockTest {

    @Test
    void shallReturnFalseIfTypeIsBlockAndSubUnitIsPresentInIssuingUnitConfigurationWithinDateTime() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfTypeIsBlockAndSubUnitIsPresentInIssuingUnitConfigurationButFromDateTimeIsNull() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }


    @Test
    void shallReturnFalseIfTypeIsBlockAndCareUnitIsPresentInCareUnitConfigurationWithinDateTime() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfTypeIsBlockAndCareUnitIsPresentInCareUnitConfigurationButFromDateTimeIsNull() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnFalseIfTypeIsBlockAndCareProviderIsPresentInCareProviderConfigurationWithinDateTime() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfTypeIsBlockAndCareProviderIsPresentInCareProviderConfigurationButFromDateTimeIsNull() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnFalseIfTypeIsBlockAndFromDateIsBeforeTodayWithToDateAsNullWithMatchingUnit() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfFromDateAndToDateIsMissingSinceRuleIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsBlockedAndUnitNotFoundInConfigurationThatIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().plusDays(1))
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnTrueIfTypeIsBlockedAndUnitNotFoundInConfigurationThatIsActive() {
      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().plusDays(1))
                          .toDateTime(null)
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(null)
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }
  }

  @Nested
  class BlockAndAllowTest {

    @Test
    void shallReturnFalseIfSubUnitIsPresentInIssuingUnitConfigurationWithinDateTimeAndBlockedIsActive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfSubUnitIsPresentInIssuingUnitConfigurationAndBlockIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .issuedOnUnit(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }


    @Test
    void shallReturnFalseIfCareUnitIsPresentInCareUnitConfigurationWithinDateTimeAndBlockedIsActive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfCareUnitIsPresentInCareUnitConfigurationWithinDateTimeButBlockedIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnFalseIfCareProviderIsPresentInCareProviderConfigurationWithinDateTimeButBlockedIsActive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }

    @Test
    void shallReturnTrueIfCareProviderIsPresentInCareProviderConfigurationWithinDateTimeButBlockedIsInactive() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(ALFA_REGIONEN.hsaId().id()))
                          .fromDateTime(null)
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(result);
    }

    @Test
    void shallReturnFalseIfBothAllowAndBlockIsActiveWithNoMatchingUnit() {

      final var actionEvaluation = ActionEvaluation.builder()
          .subUnit(ALFA_MEDICINSKT_CENTRUM)
          .careUnit(ALFA_VARDCENTRAL)
          .careProvider(ALFA_REGIONEN)
          .build();

      final var certificateAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder()
              .certificateType(FK_3226_TYPE)
              .configuration(
                  List.of(
                      CertificateAccessUnitConfiguration.builder()
                          .type(ALLOW)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build(),
                      CertificateAccessUnitConfiguration.builder()
                          .type(BLOCK)
                          .careProviders(List.of(BETA_HUDMOTTAGNINGEN.hsaId().id()))
                          .fromDateTime(LocalDateTime.now().minusDays(1))
                          .toDateTime(LocalDateTime.now().plusDays(1))
                          .build()
                  )
              )
              .build()
      );

      doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_TYPE);

      final var result = actionRuleCertificateTypeActiveForUnit.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertFalse(result);
    }
  }
}