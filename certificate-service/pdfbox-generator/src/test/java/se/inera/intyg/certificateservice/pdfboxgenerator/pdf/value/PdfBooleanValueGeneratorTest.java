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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfValueType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfBooleanValueGeneratorTest {

  private static final ElementId QUESTION_ELEMENT_ID = new ElementId("52.5");
  private static final FieldId QUESTION_FIELD_ID = new FieldId("52.5");
  private static final String YES_PDF_FIELD_ID = "form1[0].#subform[1].ksr_Ja[0]";
  private static final String NO_PDF_FIELD_ID = "form1[0].#subform[1].ksr_Nej[0]";
  private static final String CHECKBOX_VALUE = "1";

  private static final PdfBooleanValueGenerator pdfBooleanValueGenerator = new PdfBooleanValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(PdfValueType.BOOLEAN, pdfBooleanValueGenerator.getType());
  }

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfBooleanValueGenerator.generate(certificate,
              QUESTION_ELEMENT_ID, YES_PDF_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var result = pdfBooleanValueGenerator.generate(certificate,
          QUESTION_ELEMENT_ID, YES_PDF_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfBooleanValueGenerator.generate(certificate,
              QUESTION_ELEMENT_ID, YES_PDF_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var result = pdfBooleanValueGenerator.generate(certificate,
          QUESTION_ELEMENT_ID, YES_PDF_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithBooleanValueTrue() {
      final var expected = List.of(
          PdfField.builder()
              .id(YES_PDF_FIELD_ID)
              .value(CHECKBOX_VALUE)
              .build()
      );
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ELEMENT_ID)
                  .value(ElementValueBoolean.builder()
                      .booleanId(QUESTION_FIELD_ID)
                      .value(true)
                      .build())
                  .build()
          )
      );

      final var result = pdfBooleanValueGenerator.generate(certificate,
          QUESTION_ELEMENT_ID, YES_PDF_FIELD_ID);

      assertEquals(expected, result);
    }

    @Test
    void shouldSetValueIfElementDataWithBooleanValueFalse() {
      final var expected = List.of(
          PdfField.builder()
              .id(NO_PDF_FIELD_ID)
              .value(CHECKBOX_VALUE)
              .build()
      );
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ELEMENT_ID)
                  .value(ElementValueBoolean.builder()
                      .booleanId(QUESTION_FIELD_ID)
                      .value(false)
                      .build())
                  .build()
          )
      );

      final var result = pdfBooleanValueGenerator.generate(certificate,
          QUESTION_ELEMENT_ID, YES_PDF_FIELD_ID);

      assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotBooleanValue() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ELEMENT_ID)
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
          () -> pdfBooleanValueGenerator.generate(certificate,
              QUESTION_ELEMENT_ID,
              YES_PDF_FIELD_ID)
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
          pdfBooleanValueGenerator.generate(certificate,
              QUESTION_ELEMENT_ID,
              YES_PDF_FIELD_ID)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk3226CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}
