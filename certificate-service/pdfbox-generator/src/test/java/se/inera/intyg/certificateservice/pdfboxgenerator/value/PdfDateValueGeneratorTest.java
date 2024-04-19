package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class PdfDateValueGeneratorTest {

  private static final String FIELD_EXISTS = "form1[0].#subform[0].flt_dat[0]";
  private static final LocalDate VALUE = LocalDate.now();
  private static final ElementId QUESTION_ID = new ElementId("1");

  PDAcroForm pdAcroForm;

  private static final PdfDateValueGenerator pdfDateValueGenerator = new PdfDateValueGenerator();

  @BeforeEach
  void setup() throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    final var inputStream = classloader.getResourceAsStream("fk7211_v1.pdf");
    final var document = Loader.loadPDF(inputStream.readAllBytes());
    final var documentCatalog = document.getDocumentCatalog();
    pdAcroForm = documentCatalog.getAcroForm();
  }

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfDateValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
              FIELD_EXISTS));
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() throws IOException {
      final var certificate = buildCertificate(null);

      pdfDateValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID, FIELD_EXISTS);

      assertEquals("", pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfDateValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
              FIELD_EXISTS));
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() throws IOException {
      final var certificate = buildCertificate(Collections.emptyList());

      pdfDateValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID, FIELD_EXISTS);

      assertEquals("", pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithDateValue() throws IOException {
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

      pdfDateValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID, FIELD_EXISTS);

      assertEquals(VALUE.toString(), pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotDateValue() throws IOException {
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
              .generate(pdAcroForm, certificate, QUESTION_ID, FIELD_EXISTS)
      );
    }

    @Test
    void shouldThrowExceptionIfGivenQuestionIdIsNotInElementData() throws IOException {
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

      assertThrows(
          IllegalStateException.class,
          () -> pdfDateValueGenerator
              .generate(pdAcroForm, certificate, QUESTION_ID, FIELD_EXISTS)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7443CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}