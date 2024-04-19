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

class PdfTextValueGeneratorTest {

  private static final String FIELD_EXISTS = "form1[0].#subform[0].flt_txtDiagnos[0]";
  private static final String VALUE = "Diagnos är okänd men symtomen är hosta.";
  private static final ElementId QUESTION_SYMPTOM_ID = new ElementId("2");

  PDAcroForm pdAcroForm;

  private static final PdfTextValueGenerator pdfTextValueGenerator = new PdfTextValueGenerator();

  @BeforeEach
  void setup() throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    final var inputStream = classloader.getResourceAsStream("fk7443_v1.pdf");
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
          () -> pdfTextValueGenerator.generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID,
              FIELD_EXISTS));
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() throws IOException {
      final var certificate = buildCertificate(null);

      pdfTextValueGenerator.generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID, FIELD_EXISTS);

      assertEquals("", pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfTextValueGenerator.generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID,
              FIELD_EXISTS));
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() throws IOException {
      final var certificate = buildCertificate(Collections.emptyList());

      pdfTextValueGenerator.generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID, FIELD_EXISTS);

      assertEquals("", pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithTextValue() throws IOException {
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

      pdfTextValueGenerator.generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID, FIELD_EXISTS);

      assertEquals(VALUE, pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotTextValue() throws IOException {
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
              .generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID, FIELD_EXISTS)
      );
    }

    @Test
    void shouldThrowExceptionIfGivenQuestionIdIsNotInElementData() throws IOException {
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
              .generate(pdAcroForm, certificate, QUESTION_SYMPTOM_ID, FIELD_EXISTS)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7443CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}