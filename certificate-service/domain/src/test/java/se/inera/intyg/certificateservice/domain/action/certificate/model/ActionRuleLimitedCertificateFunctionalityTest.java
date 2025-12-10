package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.SEND;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.AG7804_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ag7804CertificateBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedActionConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityActionsConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;

@ExtendWith(MockitoExtension.class)
class ActionRuleLimitedCertificateFunctionalityTest {

  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  private ActionRuleLimitedCertificateFunctionality actionRuleLimitedCertificateFunctionality;

  @BeforeEach
  void setUp() {
    actionRuleLimitedCertificateFunctionality = new ActionRuleLimitedCertificateFunctionality(
        certificateActionConfigurationRepository, SEND);
  }

  @Test
  void shouldReturnFalseIfCertificateIsMissing() {
    final var result = actionRuleLimitedCertificateFunctionality.evaluate(Optional.empty(),
        Optional.empty());

    assertFalse(result);
  }

  @Test
  void shouldReturnTrueWhenCertificateIsLatestMajorVersion() {
    final var result = actionRuleLimitedCertificateFunctionality.evaluate(
        Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenCertificateIsNotLatestMajorVersionAndHasConfiguration() {
    final var inactiveConfigurations =
        LimitedCertificateFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("1.0"))
            .configuration(
                LimitedCertificateFunctionalityActionsConfiguration.builder()
                    .build()
            )
            .build();

    final var certificate = buildNotLatestMajorVersionCertificate();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id()))
        .thenReturn(inactiveConfigurations);

    final var result = actionRuleLimitedCertificateFunctionality.evaluate(Optional.of(certificate)
        , Optional.empty());
    assertFalse(result);
  }

  @Test
  void shouldReturnTrueWhenCertificateIsNotLatestMajorVersionAndDoesNotHaveConfiguration() {

    final var certificate = buildNotLatestMajorVersionCertificate();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id()))
        .thenReturn(null);

    final var result = actionRuleLimitedCertificateFunctionality.evaluate(Optional.of(certificate)
        , Optional.empty());
    assertTrue(result);
  }

  @Test
  void shouldReturnTrueWhenCertificateIsNotLatestMajorVersionAndHasConfigurationWithActionDateInFuture() {
    final var limitedFunctionalityConfigurations =
        LimitedCertificateFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("2.0"))
            .configuration(
                LimitedCertificateFunctionalityActionsConfiguration.builder()
                    .actions(
                        List.of(
                            LimitedActionConfiguration.builder()
                                .type("SEND")
                                .untilDateTime(LocalDateTime.now().plusDays(5))
                                .build()
                        )
                    )
                    .build()
            )
            .build();

    final var certificate = buildNotLatestMajorVersionCertificate();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id()))
        .thenReturn(limitedFunctionalityConfigurations);

    final var result = actionRuleLimitedCertificateFunctionality.evaluate(Optional.of(certificate)
        , Optional.empty());
    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenCertificateIsNotLatestMajorVersionAndHasConfigurationWithActionDateInPast() {
    final var limitedFunctionalityConfigurations =
        LimitedCertificateFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("2.0"))
            .configuration(
                LimitedCertificateFunctionalityActionsConfiguration.builder()
                    .actions(
                        List.of(
                            LimitedActionConfiguration.builder()
                                .type("SEND")
                                .untilDateTime(LocalDateTime.now().minusDays(5))
                                .build()
                        )
                    )
                    .build()
            )
            .build();
    final var certificate = buildNotLatestMajorVersionCertificate();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id()))
        .thenReturn(limitedFunctionalityConfigurations);

    final var result = actionRuleLimitedCertificateFunctionality.evaluate(Optional.of(certificate)
        , Optional.empty());
    assertFalse(result);
  }

  @Test
  void shouldReturnFalseWhenCertificateIsNotLatestMajorVersionAndHasConfigurationWithoutActions() {
    final var limitedFunctionalityConfigurations =
        LimitedCertificateFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("2.0"))
            .configuration(
                LimitedCertificateFunctionalityActionsConfiguration.builder()
                    .build()
            )
            .build();

    final var certificate = buildNotLatestMajorVersionCertificate();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id()))
        .thenReturn(limitedFunctionalityConfigurations);

    final var result = actionRuleLimitedCertificateFunctionality.evaluate(Optional.of(certificate)
        , Optional.empty());
    assertFalse(result);
  }

  private static MedicalCertificate buildNotLatestMajorVersionCertificate() {
    final var inactiveModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType("type"))
                .version(new CertificateVersion("2.0"))
                .build()
        )
        .activeFrom(LocalDateTime.now().minusDays(5))
        .build();

    final var activeModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType("type"))
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .activeFrom(LocalDateTime.now().minusDays(5))
        .build();

    final var certificateModel = inactiveModel.withVersions(List.of(inactiveModel, activeModel));

    return ag7804CertificateBuilder()
        .certificateModel(certificateModel)
        .build();
  }
}