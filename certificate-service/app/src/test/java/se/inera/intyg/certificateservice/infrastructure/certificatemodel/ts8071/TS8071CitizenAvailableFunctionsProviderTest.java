package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType.ATTENTION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ag7804CertificateBuilder;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.DefaultCitizenAvailableFunctionsProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityActionsConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;

@ExtendWith(MockitoExtension.class)
class TS8071CitizenAvailableFunctionsProviderTest {

  private TS8071CitizenAvailableFunctionsProvider provider;

  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  private Certificate certificate;

  @BeforeEach
  void setUp() {
    provider = new TS8071CitizenAvailableFunctionsProvider(
        certificateActionConfigurationRepository);
  }

  @Test
  void shouldShowAttentionFunctionWhenCertificateDoesNotHaveFullFunctionality() {
    certificate = getNotLatestMajorVersionCertificate();
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
  void shouldShowSendFunctionEnabledWhenCertificateHasFullFunctionality() {
    certificate = getLatestMajorVersionCertificate(false);

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
  void shouldShowSendFunctionDisabledWhenCertificateHasFullFunctionalityAndIsAlreadySent() {
    certificate = getLatestMajorVersionCertificate(true);

    final var expectedSendFunction = CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
        .enabled(false)
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
    certificate = getNotLatestMajorVersionCertificate();
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
    certificate = getLatestMajorVersionCertificate(true);

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
    final var inactiveModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("2.0"))
                .build()
        )
        .activeFrom(LocalDateTime.now().plusDays(1))
        .build();

    final var activeModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("3.0"))
                .build()
        )
        .activeFrom(LocalDateTime.now().minusDays(1))
        .build();

    final var certificateModel = inactiveModel.withVersions(List.of(inactiveModel, activeModel));

    return ag7804CertificateBuilder()
        .certificateModel(certificateModel)
        .build();
  }

  private static MedicalCertificate getLatestMajorVersionCertificate(boolean sent) {
    final var model = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .version(new CertificateVersion("2.0"))
                .build()
        )
        .activeFrom(LocalDateTime.now().plusDays(1))
        .build();

    final var certificateModel = model.withVersions(List.of(model));

    return ag7804CertificateBuilder()
        .certificateModel(certificateModel)
        .sent(
            sent ? Sent.builder().build() : null
        )
        .build();
  }

  @Nested
  class ReplacedCerificateFunction {

    @Test
    void shouldNotReturnSendAndPrintFunctionIfCertificateIsReplaced() {

      final var replacedCertificate = (Certificate) ag7804CertificateBuilder()
          .children(
              List.of(
                  Relation.builder()
                      .type(RelationType.REPLACE)
                      .certificate(
                          ag7804CertificateBuilder()
                              .status(Status.SIGNED)
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var actual = new DefaultCitizenAvailableFunctionsProvider()
          .of(replacedCertificate);

      assertAll(
          () -> assertTrue(
              actual.stream().noneMatch(
                  function -> function.type() == CitizenAvailableFunctionType.SEND_CERTIFICATE
              )
          ),
          () -> assertTrue(
              actual.stream().noneMatch(
                  function -> function.type() == CitizenAvailableFunctionType.PRINT_CERTIFICATE
              )
          )
      );
    }

    @Test
    void shouldReturnSendAndPrintFunctionIfCertificateIsNotReplaced() {

      final var notReplacedCertificate = (Certificate) ag7804CertificateBuilder().build();

      final var actual = new DefaultCitizenAvailableFunctionsProvider()
          .of(notReplacedCertificate);

      assertAll(
          () -> assertTrue(
              actual.stream().anyMatch(
                  function -> function.type() == CitizenAvailableFunctionType.SEND_CERTIFICATE
              )
          ),
          () -> assertTrue(
              actual.stream().anyMatch(
                  function -> function.type() == CitizenAvailableFunctionType.PRINT_CERTIFICATE
              )
          )
      );
    }
  }

  @Nested
  class ComplementedFunction {

    @Test
    void shouldNotReturnSendAndPrintFunctionIfCertificateIsComplemented() {

      final var complementedCertificate = (Certificate) ag7804CertificateBuilder()
          .children(
              List.of(
                  Relation.builder()
                      .type(RelationType.COMPLEMENT)
                      .certificate(
                          ag7804CertificateBuilder()
                              .status(Status.SIGNED)
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var actual = new DefaultCitizenAvailableFunctionsProvider()
          .of(complementedCertificate);

      assertAll(
          () -> assertTrue(
              actual.stream().noneMatch(
                  function -> function.type() == CitizenAvailableFunctionType.SEND_CERTIFICATE
              )
          ),
          () -> assertTrue(
              actual.stream().noneMatch(
                  function -> function.type() == CitizenAvailableFunctionType.PRINT_CERTIFICATE
              )
          )
      );
    }

    @Test
    void shouldReturnSendAndPrintFunctionIfCertificateIsNotComplemented() {

      final var notComplementedCertificate = (Certificate) ag7804CertificateBuilder().build();

      final var actual = new DefaultCitizenAvailableFunctionsProvider()
          .of(notComplementedCertificate);

      assertAll(
          () -> assertTrue(
              actual.stream().anyMatch(
                  function -> function.type() == CitizenAvailableFunctionType.SEND_CERTIFICATE
              )
          ),
          () -> assertTrue(
              actual.stream().anyMatch(
                  function -> function.type() == CitizenAvailableFunctionType.PRINT_CERTIFICATE
              )
          )
      );
    }
  }
}