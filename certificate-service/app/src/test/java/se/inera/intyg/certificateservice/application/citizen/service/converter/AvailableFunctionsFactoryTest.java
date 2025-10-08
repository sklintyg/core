package se.inera.intyg.certificateservice.application.citizen.service.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionDTO;
import se.inera.intyg.certificateservice.application.common.dto.InformationDTO;
import se.inera.intyg.certificateservice.application.common.dto.InformationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateAvailableFunctionsProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

class AvailableFunctionsFactoryTest {

  @Test
  void shouldReturnSendAndPrintFunctionsWhenProviderIsNull() {
    final var certificate = mock(Certificate.class);
    final var certificateModel = mock(CertificateModel.class);
    when(certificate.certificateModel()).thenReturn(certificateModel);
    when(certificateModel.availableFunctionsProvider()).thenReturn(null);
    when(certificate.isSendActiveForCitizen()).thenReturn(true);
    when(certificate.fileName()).thenReturn("file.pdf");

    final var result = AvailableFunctionsFactory.get(certificate);

    final var expectedSend = AvailableFunctionDTO.builder()
        .title(AvailableFunctionsFactory.SEND_CERTIFICATE_TITLE)
        .name(AvailableFunctionsFactory.SEND_CERTIFICATE_NAME)
        .type(
            se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionType.SEND_CERTIFICATE)
        .body(AvailableFunctionsFactory.SEND_CERTIFICATE_BODY)
        .enabled(true)
        .build();

    final var expectedPrint = AvailableFunctionDTO.builder()
        .name(AvailableFunctionsFactory.AVAILABLE_FUNCTION_PRINT_NAME)
        .type(
            se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionType.PRINT_CERTIFICATE)
        .information(List.of(
            InformationDTO.builder()
                .type(InformationType.FILENAME)
                .text("file.pdf")
                .build()
        ))
        .enabled(true)
        .build();

    assertThat(result).containsExactlyInAnyOrder(expectedSend, expectedPrint);
  }

  @Test
  void shouldReturnFunctionsFromProviderWhenProviderIsNotNull() {
    final var certificate = mock(Certificate.class);
    final var certificateModel = mock(CertificateModel.class);
    final var availableFunctionsProvider = mock(CertificateAvailableFunctionsProvider.class);
    final var expectedProviderFunctions = List.of(
        AvailableFunctionDTO.builder()
            .name("Custom Function")
            .type(
                se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionType.PRINT_CERTIFICATE)
            .enabled(true)
            .information(List.of())
            .build()
    );
    final var providerFunctions = List.of(
        AvailableFunction.builder()
            .name("Custom Function")
            .type(AvailableFunctionType.PRINT_CERTIFICATE)
            .enabled(true)
            .build()
    );
    when(certificate.certificateModel()).thenReturn(certificateModel);
    when(certificateModel.availableFunctionsProvider()).thenReturn(availableFunctionsProvider);
    when(availableFunctionsProvider.of(certificate)).thenReturn(providerFunctions);

    assertEquals(expectedProviderFunctions, AvailableFunctionsFactory.get(certificate));
  }
}

