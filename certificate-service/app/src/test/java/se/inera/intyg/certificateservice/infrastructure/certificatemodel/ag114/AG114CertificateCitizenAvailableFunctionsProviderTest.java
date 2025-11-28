package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionInformationType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.DefaultCitizenAvailableFunctionsProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@ExtendWith(MockitoExtension.class)
class AG114CertificateCitizenAvailableFunctionsProviderTest {

  private AG114CitizenAvailableFunctionsProvider provider;

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateModel certificateModel;

  @BeforeEach
  void setUp() {
    provider = new AG114CitizenAvailableFunctionsProvider();
    when(certificate.certificateModel()).thenReturn(certificateModel);
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
      when(certificateModel.fileName()).thenReturn("ag114-certificate.pdf");

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

      assertEquals(1, result.size());
      assertEquals(expectedCustomizeFunction, result.getFirst());
    }

    @Test
    void shouldIncludeNormalPrintFunctionWhenDiagnosisNotIncluded() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID))
          .thenReturn(Optional.of(ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()));
      when(certificateModel.fileName()).thenReturn("ag114-certificate.pdf");

      final var result = provider.of(certificate);

      assertEquals(1, result.size());
      assertEquals(CitizenAvailableFunctionType.PRINT_CERTIFICATE, result.getFirst().type());
    }

    @Test
    void shouldIncludeNormalPrintFunctionWhenDiagnosisElementMissing() {
      when(certificate.getElementDataById(QUESTION_FORMEDLA_DIAGNOS_ID)).thenReturn(
          Optional.empty());

      final var result = provider.of(certificate);

      assertEquals(1, result.size());
      assertEquals(CitizenAvailableFunctionType.PRINT_CERTIFICATE, result.getFirst().type());
    }
  }

  @Nested
  class ReplacedCerificateFunction {

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
  }

  @Nested
  class ComplementedFunction {

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
}
