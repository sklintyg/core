package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.PERIOD_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.QUESTION_SYMPTOM_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.SYMPTOM_FIELD_ID;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfDateRangeListValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfTextValueGenerator;

@ExtendWith(MockitoExtension.class)
class FK7443PdfFillServiceTest {

  private static final String TEXT = "Ett exempel på en diagnos, J10, och även symtom: hosta och feber.";
  private static final PdfField SYMPTOM_FIELD = PdfField.builder()
      .id(SYMPTOM_FIELD_ID)
      .value(TEXT)
      .build();
  private static final ElementData SYMPTOM_ELEMENT_DATA = ElementData.builder()
      .id(QUESTION_SYMPTOM_ID)
      .value(
          ElementValueText.builder()
              .text(TEXT)
              .build()
      )
      .build();

  private static final DateRange DATE_RANGE = DateRange.builder()
      .dateRangeId(new FieldId(WorkCapacityType.HALVA.toString()))
      .to(LocalDate.now().plusDays(10))
      .from(LocalDate.now())
      .build();
  private static final PdfField PERIOD_FIELD_TO = PdfField.builder()
      .id(PERIOD_FIELD_ID_PREFIX + ".flt_datLangstTillMed3[0]")
      .value(DATE_RANGE.to().toString())
      .build();
  private static final PdfField PERIOD_FIELD_FROM = PdfField.builder()
      .id(PERIOD_FIELD_ID_PREFIX + ".flt_datFranMed3[0]")
      .value(DATE_RANGE.from().toString())
      .build();
  private static final ElementData PERIOD_ELEMENT_DATA = ElementData.builder()
      .id(QUESTION_PERIOD_ID)
      .value(
          ElementValueDateRangeList.builder()
              .dateRangeList(
                  List.of(
                      DATE_RANGE
                  )
              )
              .build()
      )
      .build();
  private static final PdfField PERIOD_FIELD_CHECKBOX = PdfField.builder()
      .id(PERIOD_FIELD_ID_PREFIX + ".ksr_kryssruta3[0]")
      .value(CHECKED_BOX_VALUE)
      .build();

  @Mock
  PdfTextValueGenerator pdfTextValueGenerator;

  @Mock
  PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator;

  @InjectMocks
  private FK7443PdfFillService fk7443PdfFillService;

  @Test
  void shouldReturnPatientIdFormId() {
    assertEquals(PATIENT_ID_FIELD_ID, fk7443PdfFillService.getPatientIdFieldId());
  }

  @Test
  void shouldReturnGeneratorType() {
    assertEquals(
        fk7443CertificateBuilder().build().certificateModel().id().type(),
        fk7443PdfFillService.getType()
    );
  }

  @Test
  void shouldNotSetValueIfNotProvided() {
    final var result = fk7443PdfFillService.getFields(
        buildCertificate(Collections.emptyList())
    );

    assertTrue(result.stream().noneMatch(field -> field.getId().equals(SYMPTOM_FIELD_ID)),
        "Expected result to not contain symptom id");
  }

  @Nested
  class PdfData {

    @BeforeEach
    void setUp() {
      when(
          pdfTextValueGenerator.generate(any(Certificate.class), any(ElementId.class), anyString()))
          .thenReturn(List.of(SYMPTOM_FIELD));

      when(pdfDateRangeListValueGenerator.generate(any(Certificate.class), any(ElementId.class),
          anyString()))
          .thenReturn(List.of(PERIOD_FIELD_CHECKBOX, PERIOD_FIELD_FROM, PERIOD_FIELD_TO));
    }


    @Nested
    class Symptom {

      @Test
      void shouldSetValueIfProvided() {
        final var result = fk7443PdfFillService.getFields(
            buildCertificate(List.of(SYMPTOM_ELEMENT_DATA, PERIOD_ELEMENT_DATA))
        );

        assertTrue(result.contains(SYMPTOM_FIELD), "Expected result to contain symptom");
      }

    }

    @Nested
    class Period {

      @Test
      void shouldSetValueIfProvided() {
        final var result = fk7443PdfFillService.getFields(
            buildCertificate(List.of(SYMPTOM_ELEMENT_DATA, PERIOD_ELEMENT_DATA))
        );

        assertAll(
            () -> assertTrue(result.contains(PERIOD_FIELD_CHECKBOX)),
            () -> assertTrue(result.contains(PERIOD_FIELD_FROM)),
            () -> assertTrue(result.contains(PERIOD_FIELD_TO))
        );
      }


      @Test
      void shouldNotSetValueIfNotProvided() {
        final var result = fk7443PdfFillService.getFields(
            buildCertificate(Collections.emptyList())
        );

        assertAll(
            () -> assertTrue(result.stream().noneMatch(
                    field -> field.getId().equals(PERIOD_FIELD_ID_PREFIX + ".ksr_kryssruta2[0]")),
                "Expected result to not contain period checkbox id"),
            () -> assertTrue(result.stream().noneMatch(
                    field -> field.getId().equals(PERIOD_FIELD_ID_PREFIX + ".flt_datFranMed2[0]")),
                "Expected result to not contain period from id"),
            () -> assertTrue(result.stream().noneMatch(
                    field -> field.getId()
                        .equals(PERIOD_FIELD_ID_PREFIX + ".flt_datLangstTillMed2[0]")),
                "Expected result to not contain period to id")

        );
      }
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7211CertificateBuilder()
        .elementData(elementData)
        .build();
  }
}