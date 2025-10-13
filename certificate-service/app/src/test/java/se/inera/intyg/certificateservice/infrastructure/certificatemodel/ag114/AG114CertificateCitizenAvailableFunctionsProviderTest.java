package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionInformationType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class AG114CertificateCitizenAvailableFunctionsProviderTest {

  private AG114CitizenAvailableFunctionsProvider provider;
  private Certificate certificate;

  @BeforeEach
  void setUp() {
    provider = new AG114CitizenAvailableFunctionsProvider();
    certificate = mock(Certificate.class);
    when(certificate.fileName()).thenReturn("ag114-certificate.pdf");
    when(certificate.isSendActiveForCitizen()).thenReturn(true);
  }

  @Nested
  class SendCertificateFunction {

    @Test
    void shouldIncludeSendCertificateFunction() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var expectedSendFunction = CitizenAvailableFunction.builder()
          .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
          .name("Skicka intyg")
          .title("Skicka intyg")
          .body(
              "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. Endast mottagare som kan ta emot digitala intyg visas nedan.")
          .enabled(true)
          .information(List.of())
          .build();

      final var result = provider.of(certificate);

      assertEquals(expectedSendFunction, result.getFirst());
    }
  }

  @Nested
  class DiagnosisCustomizationFunction {

    @Test
    void shouldIncludeCustomizePrintFunctionWhenDiagnosisIsIncluded() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()));

      final var expectedSendFunction = CitizenAvailableFunction.builder()
          .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
          .name("Skicka intyg")
          .title("Skicka intyg")
          .body(
              "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. Endast mottagare som kan ta emot digitala intyg visas nedan.")
          .enabled(true)
          .information(List.of())
          .build();

      final var expectedCustomizeFunction = CitizenAvailableFunction.builder()
          .type(CitizenAvailableFunctionType.CUSTOMIZE_PRINT_CERTIFICATE)
          .name("Anpassa intyget för utskrift")
          .title("Vill du visa eller dölja diagnos?")
          .body("När du skriver ut ett läkarintyg du ska lämna till din arbetsgivare kan du "
              + "välja om du vill att din diagnos ska visas eller döljas. Ingen annan information kan döljas. ")
          .description(
              "Information om diagnos kan vara viktig för din arbetsgivare."
                  + " Det kan underlätta anpassning av din arbetssituation. Det kan också göra att du snabbare kommer tillbaka till arbetet.")
          .enabled(true)
          .information(List.of(
              CitizenAvailableFunctionInformation.builder()
                  .type(CitizenAvailableFunctionInformationType.FILENAME)
                  .text("ag114-certificate.pdf")
                  .build(),
              CitizenAvailableFunctionInformation.builder()
                  .type(CitizenAvailableFunctionInformationType.OPTIONS)
                  .text("Visa diagnos")
                  .build(),
              CitizenAvailableFunctionInformation.builder()
                  .id(QUESTION_DIAGNOS_ID)
                  .type(CitizenAvailableFunctionInformationType.OPTIONS)
                  .text("Dölj diagnos")
                  .build()
          ))
          .build();

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(expectedSendFunction, result.getFirst());
      assertEquals(expectedCustomizeFunction, result.get(1));
    }

    @Test
    void shouldIncludeNormalPrintFunctionWhenDiagnosisNotIncluded() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(CitizenAvailableFunctionType.SEND_CERTIFICATE, result.getFirst().type());
      assertEquals(CitizenAvailableFunctionType.PRINT_CERTIFICATE, result.get(1).type());
    }

    @Test
    void shouldIncludeNormalPrintFunctionWhenDiagnosisElementMissing() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID)).thenReturn(
          Optional.empty());

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(CitizenAvailableFunctionType.SEND_CERTIFICATE, result.getFirst().type());
      assertEquals(CitizenAvailableFunctionType.PRINT_CERTIFICATE, result.get(1).type());
    }
  }

  @Nested
  class ErrorHandling {

    @Test
    void shouldThrowIllegalStateExceptionWhenDiagnosisElementHasWrongType() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueText.builder().text("not a boolean").build())
              .build()));

      final var exception = assertThrows(IllegalStateException.class,
          () -> provider.isDiagnosisIncluded(certificate));

      assertEquals("Element value is not of type ElementValueBoolean", exception.getMessage());
    }
  }
}
