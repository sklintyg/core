package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFormedlaInfoOmDiagnosTillAG.QUESTION_FORMEDLA_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionInformationType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class AG7804CertificateAvailableFunctionsProviderTest {

  private AG7804CertificateAvailableFunctionsProvider provider;
  private Certificate certificate;

  @BeforeEach
  void setUp() {
    provider = new AG7804CertificateAvailableFunctionsProvider();
    certificate = mock(Certificate.class);
    when(certificate.fileName()).thenReturn("ag7804-certificate.pdf");
  }

  @Nested
  class SendCertificateFunction {

    @Test
    void shouldIncludeSendCertificateFunctionIfSmittbararAndDiagnos() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var expectedSendFunction = AvailableFunction.builder()
          .type(AvailableFunctionType.SEND_CERTIFICATE)
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
  class SmittbararpenningFunction {

    @Test
    void shouldIncludeAttentionFunctionWhenSmittbararpenningIsTrue() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()));
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var expectedSendFunction = AvailableFunction.builder()
          .type(AvailableFunctionType.SEND_CERTIFICATE)
          .name("Skicka intyg")
          .title("Skicka intyg")
          .body(
              "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. Endast mottagare som kan ta emot digitala intyg visas nedan.")
          .enabled(true)
          .information(List.of())
          .build();

      final var expectedAttentionFunction = AvailableFunction.builder()
          .type(AvailableFunctionType.ATTENTION)
          .name("Presentera informationsruta")
          .title("Avstängning enligt smittskyddslagen")
          .body(
              "I intyg som gäller avstängning enligt smittskyddslagen kan"
                  + " du inte dölja din diagnos. När du klickar på \"Skriv ut intyg\" hämtas hela intyget.")
          .enabled(true)
          .information(List.of())
          .build();

      final var result = provider.of(certificate);

      assertEquals(3, result.size());
      assertEquals(expectedSendFunction, result.getFirst());
      assertEquals(expectedAttentionFunction, result.get(1));
      assertEquals(AvailableFunctionType.PRINT_CERTIFICATE, result.get(2).type());
    }

    @Test
    void shouldNotIncludeAttentionFunctionAndNormalPrintWhenSmittbararpenningIsFalse() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(AvailableFunctionType.SEND_CERTIFICATE, result.getFirst().type());
      assertEquals(AvailableFunctionType.PRINT_CERTIFICATE, result.get(1).type());
    }

    @Test
    void shouldNotIncludeAttentionFunctionAndNormalPrintWhenSmittbararpenningElementMissing() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID)).thenReturn(
          Optional.empty());
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(AvailableFunctionType.SEND_CERTIFICATE, result.getFirst().type());
      assertEquals(AvailableFunctionType.PRINT_CERTIFICATE, result.get(1).type());
    }
  }

  @Nested
  class DiagnosisCustomizationFunction {

    @Test
    void shouldIncludeCustomizePrintFunctionWhenDiagnosisIsIncludedAndSmittbararFalse() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()));

      final var expectedSendFunction = AvailableFunction.builder()
          .type(AvailableFunctionType.SEND_CERTIFICATE)
          .name("Skicka intyg")
          .title("Skicka intyg")
          .body(
              "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. Endast mottagare som kan ta emot digitala intyg visas nedan.")
          .enabled(true)
          .information(List.of())
          .build();

      final var expectedCustomizeFunction = AvailableFunction.builder()
          .type(AvailableFunctionType.CUSTOMIZE_PRINT_CERTIFICATE)
          .name("Anpassa intyget för utskrift")
          .title("Vill du visa eller dölja diagnos?")
          .body("När du skriver ut ett läkarintyg du ska lämna till din arbetsgivare kan du "
              + "välja om du vill att din diagnos ska visas eller döljas. Ingen annan information kan döljas. ")
          .description(
              "Information om diagnos kan vara viktig för din arbetsgivare."
                  + " Det kan underlätta anpassning av din arbetssituation. Det kan också göra att du snabbare kommer tillbaka till arbetet.")
          .enabled(true)
          .information(List.of(
              AvailableFunctionInformation.builder()
                  .type(AvailableFunctionInformationType.FILENAME)
                  .text("ag7804-certificate.pdf")
                  .build(),
              AvailableFunctionInformation.builder()
                  .type(AvailableFunctionInformationType.OPTIONS)
                  .text("Visa Diagnos")
                  .build(),
              AvailableFunctionInformation.builder()
                  .id(QUESTION_DIAGNOS_ID)
                  .type(AvailableFunctionInformationType.OPTIONS)
                  .text("Dölj Diagnos")
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
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(AvailableFunctionType.SEND_CERTIFICATE, result.getFirst().type());
      assertEquals(AvailableFunctionType.PRINT_CERTIFICATE, result.get(1).type());
    }

    @Test
    void shouldIncludeNormalPrintFunctionWhenDiagnosisElementMissing() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID)).thenReturn(
          Optional.empty());

      final var result = provider.of(certificate);

      assertEquals(2, result.size());
      assertEquals(AvailableFunctionType.SEND_CERTIFICATE, result.getFirst().type());
      assertEquals(AvailableFunctionType.PRINT_CERTIFICATE, result.get(1).type());
    }
  }

  @Nested
  class ErrorHandling {

    @Test
    void shouldThrowIllegalStateExceptionWhenSmittbararpenningElementHasWrongType() {
      when(certificate.getElementDataById(QUESTION_SMITTBARARPENNING_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_SMITTBARARPENNING_ID.id()))
              .value(ElementValueText.builder().text("not a boolean").build())
              .build()));

      final var exception = assertThrows(IllegalStateException.class,
          () -> provider.isSmittbararpenning(certificate));

      assertEquals("Element value is not of type ElementValueBoolean", exception.getMessage());
    }

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
