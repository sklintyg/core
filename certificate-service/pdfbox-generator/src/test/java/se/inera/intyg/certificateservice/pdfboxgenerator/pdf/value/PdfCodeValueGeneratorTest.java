package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk3226CertificateBuilder;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfCodeValueGeneratorTest {

  private static final ElementId QUESTION_UTLATANDE_BASERAT_PA_ID = new ElementId("1");
  private static final String PDF_FIELD_ID = "form1[0].#subform[1].ksr_AkutLivshotande[0]";
  private static final FieldId CODE_FIELD_ID = new FieldId("AKUT_LIVSHOTANDE");
  private static final String CHECKBOX_VALUE = "1";
  private static final ElementValueCode ELEMENT_VALUE_CODE = ElementValueCode.builder()
      .codeId(CODE_FIELD_ID)
      .code(CODE_FIELD_ID.value())
      .build();

  private static final PdfCodeValueGenerator pdfCodeValueGenerator = new PdfCodeValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(PdfValueType.CODE, pdfCodeValueGenerator.getType());
  }

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfCodeValueGenerator.generate(certificate,
              QUESTION_UTLATANDE_BASERAT_PA_ID, PDF_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var result = pdfCodeValueGenerator.generate(certificate,
          QUESTION_UTLATANDE_BASERAT_PA_ID, PDF_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfCodeValueGenerator.generate(certificate,
              QUESTION_UTLATANDE_BASERAT_PA_ID, PDF_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var result = pdfCodeValueGenerator.generate(certificate,
          QUESTION_UTLATANDE_BASERAT_PA_ID, PDF_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithCodeValue() {
      final var expected = List.of(
          PdfField.builder()
              .id(PDF_FIELD_ID)
              .value(CHECKBOX_VALUE)
              .build()
      );
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_UTLATANDE_BASERAT_PA_ID)
                  .value(ELEMENT_VALUE_CODE)
                  .build()
          )
      );

      final var result = pdfCodeValueGenerator.generate(certificate,
          QUESTION_UTLATANDE_BASERAT_PA_ID, PDF_FIELD_ID);

      assertEquals(expected, result);
    }


    @Test
    void shouldThrowExceptionIfElementDataWithNotCodeValue() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_UTLATANDE_BASERAT_PA_ID)
                  .value(
                      ElementValueText.builder()
                          .text("")
                          .build()
                  )
                  .build()
          )
      );

      assertThrows(
          IllegalStateException.class,
          () -> pdfCodeValueGenerator.generate(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID,
              PDF_FIELD_ID)
      );
    }

    @Test
    void shouldThrowExceptionIfGivenQuestionIdIsNotInElementData() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(new ElementId("NOT_IT"))
                  .value(
                      ElementValueText.builder()
                          .text(CHECKBOX_VALUE)
                          .build()
                  )
                  .build()
          )
      );

      assertEquals(Collections.emptyList(),
          pdfCodeValueGenerator
              .generate(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID, PDF_FIELD_ID)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk3226CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}
