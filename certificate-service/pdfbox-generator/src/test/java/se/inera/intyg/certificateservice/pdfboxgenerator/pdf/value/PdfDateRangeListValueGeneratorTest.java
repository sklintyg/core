package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDateRangeListValueGeneratorTest {

  private static final String FIELD_PREFIX = "form1[0].#subform[0]";

  private static final DateRange DATE_RANGE = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.HELA.toString()))
      .from(LocalDate.now().minusDays(1))
      .to(LocalDate.now().plusDays(10))
      .build();

  private static final DateRange DATE_RANGE_2 = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.EN_FJARDEDEL.toString()))
      .from(LocalDate.now().minusDays(5))
      .to(LocalDate.now().plusDays(7))
      .build();

  private static final DateRange DATE_RANGE_3 = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.HALVA.toString()))
      .from(LocalDate.now().minusDays(5))
      .to(LocalDate.now().plusDays(7))
      .build();

  private static final DateRange DATE_RANGE_ONLY_FROM = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.EN_ATTONDEL.toString()))
      .from(LocalDate.now().minusDays(1))
      .build();

  private static final DateRange DATE_RANGE_ONLY_TO = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.TRE_FJARDEDELAR.toString()))
      .to(LocalDate.now().plusDays(10))
      .build();

  private static final ElementId QUESTION_ID = new ElementId("3");

  private static final PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator = new PdfDateRangeListValueGenerator();


  @Nested
  class NoElementData {

    @Test
    void shouldNotThrowErrorIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      assertDoesNotThrow(
          () -> pdfDateRangeListValueGenerator.generate(certificate,
              QUESTION_ID,
              FIELD_PREFIX));
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() {
      final var certificate = buildCertificate(null);

      final var result = pdfDateRangeListValueGenerator.generate(certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfDateRangeListValueGenerator.generate(certificate,
              QUESTION_ID,
              FIELD_PREFIX));
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      final var result = pdfDateRangeListValueGenerator.generate(certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class ElementDataExists {

    @Test
    void shouldSetValueIfElementDataWithDateRangeList() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ID)
                  .value(
                      ElementValueDateRangeList.builder()
                          .dateRangeList(
                              List.of(
                                  DATE_RANGE
                              )
                          )
                          .build()
                  )
                  .build()
          )
      );

      final var result = pdfDateRangeListValueGenerator.generate(certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertAll(
          () -> assertEquals(3, result.size()),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".ksr_kryssruta[0]")
                  .value("1")
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datFranMed[0]")
                  .value(DATE_RANGE.from().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datLangstTillMed[0]")
                  .value(DATE_RANGE.to().toString())
                  .build())
          )
      );
    }

    @Test
    void shouldSetValueIfElementDataWithDateRangeListIncludingSeveralDateRanges() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ID)
                  .value(
                      ElementValueDateRangeList.builder()
                          .dateRangeList(
                              List.of(
                                  DATE_RANGE,
                                  DATE_RANGE_2
                              )
                          )
                          .build()
                  )
                  .build()
          )
      );

      final var result = pdfDateRangeListValueGenerator.generate(certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertAll(
          () -> assertEquals(6, result.size()),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".ksr_kryssruta[0]")
                  .value("1")
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datFranMed[0]")
                  .value(DATE_RANGE.from().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datLangstTillMed[0]")
                  .value(DATE_RANGE.to().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".ksr_kryssruta[0]")
                  .value("1")
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datFranMed4[0]")
                  .value(DATE_RANGE_2.from().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datLangstTillMed4[0]")
                  .value(DATE_RANGE_2.to().toString())
                  .build())
          )
      );
    }

    @Test
    void shouldSetValueIfElementDataWithDateRangeListIncludingSeveralDateRangesAndSomeNotWhole() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ID)
                  .value(
                      ElementValueDateRangeList.builder()
                          .dateRangeList(
                              List.of(
                                  DATE_RANGE_ONLY_FROM,
                                  DATE_RANGE_3,
                                  DATE_RANGE_ONLY_TO
                              )
                          )
                          .build()
                  )
                  .build()
          )
      );

      final var result = pdfDateRangeListValueGenerator.generate(certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertAll(
          () -> assertEquals(7, result.size()),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".ksr_kryssruta3[0]")
                  .value("1")
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datFranMed3[0]")
                  .value(DATE_RANGE_3.from().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datLangstTillMed3[0]")
                  .value(DATE_RANGE_3.to().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".ksr_kryssruta5[0]")
                  .value("1")
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datFranMed5[0]")
                  .value(DATE_RANGE_ONLY_FROM.from().toString())
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".ksr_kryssruta2[0]")
                  .value("1")
                  .build())
          ),
          () -> assertTrue(
              result.contains(PdfField.builder()
                  .id(FIELD_PREFIX + ".flt_datLangstTillMed2[0]")
                  .value(DATE_RANGE_ONLY_TO.to().toString())
                  .build())
          )
      );
    }

    @Test
    void shouldThrowExceptionIfElementDataWithNotDateRangeListValue() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(QUESTION_ID)
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
          () -> pdfDateRangeListValueGenerator
              .generate(certificate, QUESTION_ID, FIELD_PREFIX)
      );
    }

    @Test
    void shouldThrowExceptionIfGivenQuestionIdIsNotInElementData() {
      final var certificate = buildCertificate(
          List.of(
              ElementData.builder()
                  .id(new ElementId("NOT_IT"))
                  .value(
                      ElementValueDateRangeList.builder()
                          .dateRangeList(
                              List.of(
                                  DATE_RANGE
                              )
                          )
                          .build()
                  )
                  .build()
          )
      );

      assertThrows(
          IllegalStateException.class,
          () -> pdfDateRangeListValueGenerator
              .generate(certificate, QUESTION_ID, FIELD_PREFIX)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7472CertificateBuilder()
        .elementData(elementData)
        .build();
  }

}