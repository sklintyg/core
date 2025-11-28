package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@ExtendWith(MockitoExtension.class)
class DefaultCitizenAvailableFunctionsProviderTest {

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateModel certificateModel;

  @BeforeEach
  void setUp() {
    when(certificate.certificateModel()).thenReturn(certificateModel);
  }

  @Test
  void shouldReturnSendFunction() {
    when(certificateModel.fileName()).thenReturn("fileName.pdf");

    final var expected = CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
        .title("Skicka intyg")
        .name("Skicka intyg")
        .body(
            "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. "
                + "Endast mottagare som kan ta emot digitala intyg visas nedan.")
        .enabled(true)
        .build();

    when(certificate.isSendActiveForCitizen()).thenReturn(true);

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate).getFirst();

    assertEquals(expected, actual);
  }

  @Test
  void shouldNotReturnSendFunctionIfCertificateModelIsInactive() {
    when(certificateModel.fileName()).thenReturn("fileName.pdf");
    when(certificateModel.isInactive()).thenReturn(true);

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate);

    assertTrue(
        actual.stream().noneMatch(
            function -> function.type() == CitizenAvailableFunctionType.SEND_CERTIFICATE
        )
    );
  }

  @Test
  void shouldReturnPrintFunction() {
    when(certificateModel.fileName()).thenReturn("fileName.pdf");
    final var expected = CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.PRINT_CERTIFICATE)
        .name("Intyget kan skrivas ut")
        .enabled(true)
        .information(List.of(
            CitizenAvailableFunctionInformation.builder()
                .type(CitizenAvailableFunctionInformationType.FILENAME)
                .text("fileName.pdf")
                .build()
        ))
        .build();

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate).get(1);

    assertEquals(expected, actual);
  }

  @Test
  void shouldNotReturnSendAndPrintFunctionIfCertificateIsReplaced() {
    when(certificate.isReplaced()).thenReturn(true);

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate);

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
    when(certificate.isReplaced()).thenReturn(false);
    when(certificateModel.fileName()).thenReturn("fileName.pdf");
    when(certificate.isSendActiveForCitizen()).thenReturn(true);

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate);

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

  @Test
  void shouldNotReturnSendAndPrintFunctionIfCertificateIsComplemented() {
    when(certificate.isComplemented()).thenReturn(true);

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate);

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
    when(certificate.isComplemented()).thenReturn(false);
    when(certificateModel.fileName()).thenReturn("fileName.pdf");
    when(certificate.isSendActiveForCitizen()).thenReturn(true);

    final var actual = new DefaultCitizenAvailableFunctionsProvider()
        .of(certificate);

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