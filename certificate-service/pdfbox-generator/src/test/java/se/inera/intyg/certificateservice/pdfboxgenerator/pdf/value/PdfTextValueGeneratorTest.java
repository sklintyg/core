package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfTextValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  private static final String VALUE = "Diagnos är okänd men symtomen är hosta.";
  private static final ElementId QUESTION_SYMPTOM_ID = new ElementId("2");


  private static final PdfTextValueGenerator pdfTextValueGenerator = new PdfTextValueGenerator();

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfTextValueGenerator.generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var result = pdfTextValueGenerator.generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfTextValueGenerator.generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var result = pdfTextValueGenerator.generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithTextValue() {
      final var expected = List.of(
          PdfField.builder()
              .id(FIELD_ID)
              .value(VALUE)
              .build()
      );
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_SYMPTOM_ID)
                  .value(
                      ElementValueText.builder()
                          .text(VALUE)
                          .build()
                  )
                  .build()
          )
      );

      final var result = pdfTextValueGenerator.generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID);

      assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotTextValue() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_SYMPTOM_ID)
                  .value(
                      ElementValueDate.builder()
                          .date(LocalDate.now())
                          .build()
                  )
                  .build()
          )
      );

      assertThrows(
          IllegalStateException.class,
          () -> pdfTextValueGenerator
              .generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID)
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
                          .text(VALUE)
                          .build()
                  )
                  .build()
          )
      );

      assertThrows(
          IllegalStateException.class,
          () -> pdfTextValueGenerator
              .generate(certificate, QUESTION_SYMPTOM_ID, FIELD_ID)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7443CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}