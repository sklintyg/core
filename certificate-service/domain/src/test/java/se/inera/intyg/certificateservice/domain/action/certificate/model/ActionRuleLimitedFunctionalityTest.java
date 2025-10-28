package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.SEND;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.AG7804_CERTIFICATE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedActionConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedFunctionalityActionsConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedFunctionalityConfiguration;

@ExtendWith(MockitoExtension.class)
class ActionRuleLimitedFunctionalityTest {

  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @Mock
  private CertificateModelRepository certificateModelRepository;

  private ActionRuleLimitedFunctionality actionRuleLimitedFunctionality;

  @BeforeEach
  void setUp() {
    actionRuleLimitedFunctionality = new ActionRuleLimitedFunctionality(
        certificateActionConfigurationRepository, certificateModelRepository, SEND);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenCertificateIsMissing() {
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> actionRuleLimitedFunctionality.evaluate(Optional.empty(), Optional.empty()));

    assertEquals("Certificate is required for evaluating ActionRuleInactiveCertificateType",
        illegalStateException.getMessage());
  }

  @Test
  void shouldReturnTrueWhenCertificateIsLatestMajorVersion() {
    when(certificateModelRepository.findLatestActiveByType(
        AG7804_CERTIFICATE.certificateModel().typeName())).thenReturn(
        Optional.of(AG7804_CERTIFICATE.certificateModel()));

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenCertificateIsNotLatestMajorVersionAndHasConfiguration() {
    final var inactiveConfigurations =
        LimitedFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("1.0"))
            .configuration(
                LimitedFunctionalityActionsConfiguration.builder()
                    .build()
            )
            .build();

    final var modelWithNewerVersion = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .build();

    when(certificateModelRepository.findLatestActiveByType(
        AG7804_CERTIFICATE.certificateModel().typeName())).thenReturn(
        Optional.of(modelWithNewerVersion)
    );
    when(certificateActionConfigurationRepository.findLimitedFunctionalityConfiguration(
        AG7804_CERTIFICATE.certificateModel().id()))
        .thenReturn(inactiveConfigurations);

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());
    assertFalse(result);
  }

  @Test
  void shouldReturnTrueWhenCertificateIsNotLatestMajorVersionAndDoesNotHaveConfiguration() {
    final var modelWithNewerVersion = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .build();

    when(certificateModelRepository.findLatestActiveByType(
        AG7804_CERTIFICATE.certificateModel().typeName())).thenReturn(
        Optional.of(modelWithNewerVersion)
    );
    when(certificateActionConfigurationRepository.findLimitedFunctionalityConfiguration(
        AG7804_CERTIFICATE.certificateModel().id()))
        .thenReturn(null);

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());
    assertTrue(result);
  }

  @Test
  void shouldReturnTrueWhenCertificateIsNotLatestMajorVersionAndHasConfigurationWithActionDateInFuture() {
    final var limitedFunctionalityConfigurations =
        LimitedFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("2.0"))
            .configuration(
                LimitedFunctionalityActionsConfiguration.builder()
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

    final var modelWithNewerVersion = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .build();

    when(certificateModelRepository.findLatestActiveByType(
        AG7804_CERTIFICATE.certificateModel().typeName())).thenReturn(
        Optional.of(modelWithNewerVersion)
    );
    when(certificateActionConfigurationRepository.findLimitedFunctionalityConfiguration(
        AG7804_CERTIFICATE.certificateModel().id()))
        .thenReturn(limitedFunctionalityConfigurations);

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());
    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenCertificateIsNotLatestMajorVersionAndHasConfigurationWithActionDateInPast() {
    final var limitedFunctionalityConfigurations =
        LimitedFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("2.0"))
            .configuration(
                LimitedFunctionalityActionsConfiguration.builder()
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

    final var modelWithNewerVersion = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .build();

    when(certificateModelRepository.findLatestActiveByType(
        AG7804_CERTIFICATE.certificateModel().typeName())).thenReturn(
        Optional.of(modelWithNewerVersion)
    );
    when(certificateActionConfigurationRepository.findLimitedFunctionalityConfiguration(
        AG7804_CERTIFICATE.certificateModel().id()))
        .thenReturn(limitedFunctionalityConfigurations);

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());
    assertFalse(result);
  }

  @Test
  void shouldReturnFalseWhenCertificateIsNotLatestMajorVersionAndHasConfigurationWithoutActions() {
    final var limitedFunctionalityConfigurations =
        LimitedFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("2.0"))
            .configuration(
                LimitedFunctionalityActionsConfiguration.builder()
                    .build()
            )
            .build();

    final var modelWithNewerVersion = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .build();

    when(certificateModelRepository.findLatestActiveByType(
        AG7804_CERTIFICATE.certificateModel().typeName())).thenReturn(
        Optional.of(modelWithNewerVersion)
    );
    when(certificateActionConfigurationRepository.findLimitedFunctionalityConfiguration(
        AG7804_CERTIFICATE.certificateModel().id()))
        .thenReturn(limitedFunctionalityConfigurations);

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());
    assertFalse(result);
  }
}