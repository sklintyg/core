package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;

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

class PdfDateValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_dat[0]";
  private static final LocalDate VALUE = LocalDate.now();
  private static final ElementId QUESTION_ID = new ElementId("1");

  private static final PdfDateValueGenerator pdfDateValueGenerator = new PdfDateValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(PdfValueType.DATE, pdfDateValueGenerator.getType());
  }

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfDateValueGenerator.generate(certificate, QUESTION_ID, FIELD_ID));
    }

    @Test
    void shouldReturnEmptyListIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var response = pdfDateValueGenerator.generate(certificate, QUESTION_ID, FIELD_ID);

      assertEquals(Collections.emptyList(), response);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfDateValueGenerator.generate(certificate, QUESTION_ID, FIELD_ID));
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var response = pdfDateValueGenerator.generate(certificate, QUESTION_ID, FIELD_ID);

      assertEquals(Collections.emptyList(), response);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithDateValue() {
      final var expected = List.of(
          PdfField.builder()
              .id(FIELD_ID)
              .value(VALUE.toString())
              .build()
      );

      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ID)
                  .value(
                      ElementValueDate.builder()
                          .date(VALUE)
                          .build()
                  )
                  .build()
          )
      );

      final var result = pdfDateValueGenerator.generate(certificate, QUESTION_ID, FIELD_ID);

      assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotDateValue() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ID)
                  .value(
                      ElementValueText.builder()
                          .text("TEXT")
                          .build()
                  )
                  .build()
          )
      );

      assertThrows(
          IllegalStateException.class,
          () -> pdfDateValueGenerator
              .generate(certificate, QUESTION_ID, FIELD_ID)
      );
    }

    @Test
    void shouldReturnEmptyListIfGivenQuestionIdIsNotInElementData() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(new ElementId("NOT_IT"))
                  .value(
                      ElementValueDate.builder()
                          .date(LocalDate.now())
                          .build()
                  )
                  .build()
          )
      );

      assertEquals(Collections.emptyList(),
          pdfDateValueGenerator
              .generate(certificate, QUESTION_ID, FIELD_ID)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7472CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}
