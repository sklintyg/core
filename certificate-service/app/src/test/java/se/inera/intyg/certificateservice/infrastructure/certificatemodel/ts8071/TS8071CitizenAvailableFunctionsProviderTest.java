package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType.ATTENTION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ag7804CertificateBuilder;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityActionsConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;

@ExtendWith(MockitoExtension.class)
class TS8071CitizenAvailableFunctionsProviderTest {

  private TS8071CitizenAvailableFunctionsProvider provider;

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  @BeforeEach
  void setUp() {
    provider = new TS8071CitizenAvailableFunctionsProvider(
        certificateActionConfigurationRepository);
    certificate = getNotLatestMajorVersionCertificate();
  }

  @Test
  void shouldShowAttentionFunctionWhenCertificateDoesNotHaveFullFunctionality() {
    final var expectedAttentionFunction = CitizenAvailableFunction.builder()
        .type(ATTENTION)
        .enabled(true)
        .title("Detta intyg är av en äldre version")
        .name("Presentera informationsruta")
        .body("Intyget kan inte längre skickas digitalt till mottagaren.")
        .build();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id())).thenReturn(
        getLimitedCertificateFunctionalityConfiguration());

    final var result = provider.of(certificate);

    assertAll(
        () -> assertEquals(expectedAttentionFunction, result.getFirst()),
        () -> assertEquals(2, result.size())
    );
  }

  @Test
  void shouldShowSendFunctionWhenCertificateHasFullFunctionality() {
    certificate = getLatestMajorVersionCertificate();

    final var expectedSendFunction = CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
        .enabled(true)
        .title("Skicka intyg")
        .name("Skicka intyg")
        .body("Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. "
            + "Endast mottagare som kan ta emot digitala intyg visas nedan.")
        .build();

    final var result = provider.of(certificate);

    assertAll(
        () -> assertEquals(expectedSendFunction, result.getFirst()),
        () -> assertEquals(2, result.size())
    );
  }

  @Test
  void shouldShowPrintFunctionWhenCertificateDoesNotHaveFullFunctionality() {
    final var expectedPrintFunction = CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.PRINT_CERTIFICATE)
        .enabled(true)
        .name("Intyget kan skrivas ut")
        .build();

    when(certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        certificate.certificateModel().id())).thenReturn(
        getLimitedCertificateFunctionalityConfiguration());

    final var result = provider.of(certificate);

    assertAll(
        () -> assertEquals(expectedPrintFunction, result.getLast()),
        () -> assertEquals(2, result.size())
    );
  }

  @Test
  void shouldShowPrintFunctionWhenCertificateHasFullFunctionality() {
    certificate = getLatestMajorVersionCertificate();

    final var expectedPrintFunction = CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.PRINT_CERTIFICATE)
        .enabled(true)
        .name("Intyget kan skrivas ut")
        .build();

    final var result = provider.of(certificate);

    assertAll(
        () -> assertEquals(expectedPrintFunction, result.getLast()),
        () -> assertEquals(2, result.size())
    );
  }


  private static LimitedCertificateFunctionalityConfiguration getLimitedCertificateFunctionalityConfiguration() {
    return LimitedCertificateFunctionalityConfiguration.builder()
        .certificateType("type")
        .version(List.of("1.0"))
        .configuration(
            LimitedCertificateFunctionalityActionsConfiguration.builder()
                .build()
        ).build();
  }


  private static MedicalCertificate getNotLatestMajorVersionCertificate() {
    return ag7804CertificateBuilder()
        .certificateModel(
            CertificateModel.builder()
                .certificateVersions(List.of(new CertificateVersion("3.0")))
                .id(
                    CertificateModelId.builder()
                        .version(new CertificateVersion("2.0"))
                        .build()
                )
                .build()
        ).build();
  }

  private static MedicalCertificate getLatestMajorVersionCertificate() {
    return ag7804CertificateBuilder()
        .certificateModel(
            CertificateModel.builder()
                .certificateVersions(List.of(new CertificateVersion("2.0")))
                .id(
                    CertificateModelId.builder()
                        .version(new CertificateVersion("2.0"))
                        .build()
                )
                .build()
        ).build();
  }
}