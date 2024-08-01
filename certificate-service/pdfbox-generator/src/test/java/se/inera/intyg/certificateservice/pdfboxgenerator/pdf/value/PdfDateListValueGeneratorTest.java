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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDateListValueGeneratorTest {

  private static final ElementId QUESTION_UTLATANDE_BASERAT_PA_ID = new ElementId("1");
  private static final String DATE_FIELD_ID = "form1[0].#subform[0].flt_datUl_1[0]";
  private static final String CHECKBOX_FIELD_ID = "form1[0].#subform[0].ksr_UndersokningPatient[0]";
  private static final FieldId QUESTION_FIELD_ID = new FieldId("undersokningAvPatienten");
  private static final String CHECKBOX_VALUE = "1";
  private static final LocalDate DATE_VALUE = LocalDate.now();
  private static final ElementValueDate ELEMENT_VALUE_DATE = ElementValueDate.builder()
      .dateId(QUESTION_FIELD_ID)
      .date(DATE_VALUE)
      .build();

  private static final PdfDateListValueGenerator pdfDateListValueGenerator = new PdfDateListValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(PdfValueType.DATE_LIST, pdfDateListValueGenerator.getType());
  }

  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfDateListValueGenerator.generate(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID,
              DATE_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var result = pdfDateListValueGenerator.generate(certificate,
          QUESTION_UTLATANDE_BASERAT_PA_ID,
          DATE_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfDateListValueGenerator.generate(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID,
              DATE_FIELD_ID)
      );
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var result = pdfDateListValueGenerator.generate(certificate,
          QUESTION_UTLATANDE_BASERAT_PA_ID, DATE_FIELD_ID);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithDateListValue() {
      final var expected = List.of(
          PdfField.builder()
              .id(CHECKBOX_FIELD_ID)
              .value(CHECKBOX_VALUE)
              .build(),
          PdfField.builder()
              .id(DATE_FIELD_ID)
              .value(DATE_VALUE.toString())
              .build()
      );
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_UTLATANDE_BASERAT_PA_ID)
                  .value(
                      ElementValueDateList.builder()
                          .dateList(List.of(ELEMENT_VALUE_DATE))
                          .dateListId(QUESTION_FIELD_ID)
                          .build()
                  )
                  .build()
          )
      );

      final var result = pdfDateListValueGenerator.generate(certificate,
          QUESTION_UTLATANDE_BASERAT_PA_ID, DATE_FIELD_ID);

      assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotDateListValue() {
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
          () -> pdfDateListValueGenerator
              .generate(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID, DATE_FIELD_ID)
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
          pdfDateListValueGenerator
              .generate(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID, DATE_FIELD_ID)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk3226CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}
