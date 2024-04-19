package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import static org.junit.jupiter.api.Assertions.assertAll;
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
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;

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
      .dateRangeId(new FieldId(WorkCapacityType.EN_ATTANDEL.toString()))
      .from(LocalDate.now().minusDays(1))
      .build();

  private static final DateRange DATE_RANGE_ONLY_TO = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.TRE_FJARDEDELAR.toString()))
      .to(LocalDate.now().plusDays(10))
      .build();

  private static final ElementId QUESTION_ID = new ElementId("3");

  PDAcroForm pdAcroForm;

  private static final PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator = new PdfDateRangeListValueGenerator();

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
          () -> pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate,
              QUESTION_ID,
              FIELD_PREFIX));
    }

    @Test
    void shouldNotSetValueIfElementDataIsNull() throws IOException {
      final var certificate = buildCertificate(null);

      pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertEquals("", pdAcroForm.getField(FIELD_PREFIX).getValueAsString());
    }

    @Test
    void shouldNotThrowErrorIfElementDataIsEmpty() {
      final var certificate = buildCertificate(Collections.emptyList());

      assertDoesNotThrow(
          () -> pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate,
              QUESTION_ID,
              FIELD_PREFIX));
    }

    @Test
    void shouldNotSetValueIfElementDataIsEmpty() throws IOException {
      final var certificate = buildCertificate(Collections.emptyList());

      pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertEquals("", pdAcroForm.getField(FIELD_PREFIX).getValueAsString());
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

      pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertAll(
          () -> assertEquals(
              "1",
              pdAcroForm.getField(FIELD_PREFIX + ".ksr_kryssruta[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE.from().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datFranMed[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE.to().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datLangstTillMed[0]").getValueAsString()
          )
      );
    }

    @Test
    void shouldSetValueIfElementDataWithDateRangeListInclduingSeveralDateRanges() {
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

      pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertAll(
          () -> assertEquals(
              "1",
              pdAcroForm.getField(FIELD_PREFIX + ".ksr_kryssruta[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE.from().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datFranMed[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE.to().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datLangstTillMed[0]").getValueAsString()
          ),
          () -> assertEquals(
              "1",
              pdAcroForm.getField(FIELD_PREFIX + ".ksr_kryssruta4[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE_2.from().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datFranMed4[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE_2.to().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datLangstTillMed4[0]").getValueAsString()
          )
      );
    }

    @Test
    void shouldSetValueIfElementDataWithDateRangeListInclduingSeveralDateRangesAndSomeNotWhole() {
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

      pdfDateRangeListValueGenerator.generate(pdAcroForm, certificate, QUESTION_ID,
          FIELD_PREFIX);

      assertAll(
          () -> assertEquals(
              "1",
              pdAcroForm.getField(FIELD_PREFIX + ".ksr_kryssruta5[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE_ONLY_FROM.from().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datFranMed5[0]").getValueAsString()
          ),
          () -> assertEquals(
              "",
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datLangstTillMed5[0]").getValueAsString()
          ),
          () -> assertEquals(
              "1",
              pdAcroForm.getField(FIELD_PREFIX + ".ksr_kryssruta2[0]").getValueAsString()
          ),
          () -> assertEquals(
              "",
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datFranMed2[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE_ONLY_TO.to().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datLangstTillMed2[0]").getValueAsString()
          ),
          () -> assertEquals(
              "1",
              pdAcroForm.getField(FIELD_PREFIX + ".ksr_kryssruta3[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE_3.from().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datFranMed3[0]").getValueAsString()
          ),
          () -> assertEquals(
              DATE_RANGE_3.to().toString(),
              pdAcroForm.getField(FIELD_PREFIX + ".flt_datLangstTillMed3[0]").getValueAsString()
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
              .generate(pdAcroForm, certificate, QUESTION_ID, FIELD_PREFIX)
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
              .generate(pdAcroForm, certificate, QUESTION_ID, FIELD_PREFIX)
      );
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7443CertificateBuilder()
        .elementData(elementData)
        .build();
  }

}