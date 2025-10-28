package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.AG7804_CERTIFICATE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.LimitedFunctionalityConfiguration;

@ExtendWith(MockitoExtension.class)
class ActionRuleLimitedFunctionalityTest {

  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @Mock
  private CertificateModelRepository certificateModelRepository;

  @InjectMocks
  private ActionRuleLimitedFunctionality actionRuleLimitedFunctionality;

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
    final var inactiveConfigurations = List.of(
        LimitedFunctionalityConfiguration.builder()
            .certificateType("type")
            .version(List.of("1.0"))
            .build()
    );
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
        .thenReturn(Collections.emptyList());

    final var result = actionRuleLimitedFunctionality.evaluate(Optional.of(AG7804_CERTIFICATE)
        , Optional.empty());
    assertTrue(result);
  }
}