package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk3226CertificateBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfValueType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDiagnosisListValueGeneratorTest {

  private static final ElementId QUESTION_DIAGNOSIS_ID = new ElementId("58");
  private static final String CODE_FIELD_ID_1 = "form1[0].#subform[0].flt_txtDiaKod1[0]";
  private static final String CODE_FIELD_ID_2 = "form1[0].#subform[0].flt_txtDiaKod2[0]";
  private static final String CODE_FIELD_ID_3 = "form1[0].#subform[0].flt_txtDiaKod3[0]";
  private static final String DESCRIPTION_FIELD_ID = "form1[0].#subform[0].flt_txtDiagnoser[0]";

  private static final ElementValueDiagnosisList ELEMENT_VALUE_DIAGNOSE_LIST = ElementValueDiagnosisList.builder()
      .diagnoses(
          List.of(
              ElementValueDiagnosis.builder()
                  .id(new FieldId("huvuddiagnos"))
                  .description("description")
                  .terminology("terminology")
                  .code("ABC")
                  .build()
          )
      )
      .build();

  private static final PdfDiagnosisListValueGenerator PDF_DIAGNOSIS_LIST_VALUE_GENERATOR = new PdfDiagnosisListValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(PdfValueType.DIAGNOSE_LIST, PDF_DIAGNOSIS_LIST_VALUE_GENERATOR.getType());
  }

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> PDF_DIAGNOSIS_LIST_VALUE_GENERATOR.generate(certificate,
              QUESTION_DIAGNOSIS_ID,
              DESCRIPTION_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var result = PDF_DIAGNOSIS_LIST_VALUE_GENERATOR.generate(certificate,
          QUESTION_DIAGNOSIS_ID,
          DESCRIPTION_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> PDF_DIAGNOSIS_LIST_VALUE_GENERATOR.generate(certificate,
              QUESTION_DIAGNOSIS_ID,
              DESCRIPTION_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var result = PDF_DIAGNOSIS_LIST_VALUE_GENERATOR.generate(certificate,
          QUESTION_DIAGNOSIS_ID, DESCRIPTION_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithDiagnoseListValue() {
      final var expected = List.of(
          PdfField.builder()
              .id(DESCRIPTION_FIELD_ID)
              .value("description")
              .build(),
          PdfField.builder()
              .id(CODE_FIELD_ID_1)
              .value("A")
              .build(),
          PdfField.builder()
              .id(CODE_FIELD_ID_2)
              .value("B")
              .build(),
          PdfField.builder()
              .id(CODE_FIELD_ID_3)
              .value("C")
              .build()
      );
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_DIAGNOSIS_ID)
                  .value(ELEMENT_VALUE_DIAGNOSE_LIST)
                  .build()
          )
      );

      final var result = PDF_DIAGNOSIS_LIST_VALUE_GENERATOR.generate(certificate,
          QUESTION_DIAGNOSIS_ID, DESCRIPTION_FIELD_ID);

      assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotDiagnosisListValue() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_DIAGNOSIS_ID)
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
          () -> PDF_DIAGNOSIS_LIST_VALUE_GENERATOR
              .generate(certificate, QUESTION_DIAGNOSIS_ID, DESCRIPTION_FIELD_ID)
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
                          .text("")
                          .build()
                  )
                  .build()
          )
      );

      assertEquals(Collections.emptyList(),
          PDF_DIAGNOSIS_LIST_VALUE_GENERATOR
              .generate(certificate, QUESTION_DIAGNOSIS_ID, DESCRIPTION_FIELD_ID)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk3226CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}
