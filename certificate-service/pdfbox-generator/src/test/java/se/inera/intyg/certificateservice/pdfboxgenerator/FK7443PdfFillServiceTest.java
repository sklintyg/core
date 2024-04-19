package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7443PdfFillService.DIAGNOSIS_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7443PdfFillService.PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7443PdfFillService.PERIOD_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7443PdfFillService.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7443PdfFillService.QUESTION_SYMPTOM_ID;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;

@ExtendWith(MockitoExtension.class)
class FK7443PdfFillServiceTest {

  private static final String TEXT = "Ett exempel på en diagnos, J10, och även symtom: hosta och feber.";
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

  private PDAcroForm pdAcroForm;

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

  @Nested
  class PdfData {

    @BeforeEach
    void setup() throws IOException {
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      final var inputStream = classloader.getResourceAsStream("fk7443_v1.pdf");
      final var document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      pdAcroForm = documentCatalog.getAcroForm();
    }

    @Nested
    class Symptom {

      @Test
      void shouldSetValueIfProvided() throws IOException {
        fk7443PdfFillService.fillDocument(
            pdAcroForm,
            buildCertificate(List.of(SYMPTOM_ELEMENT_DATA, PERIOD_ELEMENT_DATA))
        );

        assertEquals(
            TEXT,
            pdAcroForm.getField(DIAGNOSIS_FIELD_ID).getValueAsString()
        );
      }


      @Test
      void shouldNotSetValueIfNotProvided() throws IOException {
        fk7443PdfFillService.fillDocument(
            pdAcroForm,
            buildCertificate(Collections.emptyList())
        );

        assertEquals(
            "",
            pdAcroForm.getField(DIAGNOSIS_FIELD_ID).getValueAsString()
        );
      }
    }

    @Nested
    class Period {

      @Test
      void shouldSetValueIfProvided() throws IOException {
        fk7443PdfFillService.fillDocument(
            pdAcroForm,
            buildCertificate(List.of(SYMPTOM_ELEMENT_DATA, PERIOD_ELEMENT_DATA))
        );

        assertAll(
            () -> assertEquals(
                "1",
                pdAcroForm.getField(PERIOD_FIELD_ID_PREFIX + ".ksr_kryssruta2[0]")
                    .getValueAsString()
            ),
            () -> assertEquals(
                DATE_RANGE.from().toString(),
                pdAcroForm.getField(PERIOD_FIELD_ID_PREFIX + ".flt_datFranMed2[0]")
                    .getValueAsString()
            ),
            () -> assertEquals(
                DATE_RANGE.to().toString(),
                pdAcroForm.getField(PERIOD_FIELD_ID_PREFIX + ".flt_datLangstTillMed2[0]")
                    .getValueAsString()
            )
        );
      }


      @Test
      void shouldNotSetValueIfNotProvided() throws IOException {
        fk7443PdfFillService.fillDocument(
            pdAcroForm,
            buildCertificate(Collections.emptyList())
        );

        assertAll(
            () -> assertEquals(
                "Off",
                pdAcroForm.getField(PERIOD_FIELD_ID_PREFIX + ".ksr_kryssruta2[0]")
                    .getValueAsString()
            ),
            () -> assertEquals(
                "",
                pdAcroForm.getField(PERIOD_FIELD_ID_PREFIX + ".flt_datFranMed2[0]")
                    .getValueAsString()
            ),
            () -> assertEquals(
                "",
                pdAcroForm.getField(PERIOD_FIELD_ID_PREFIX + ".flt_datLangstTillMed2[0]")
                    .getValueAsString()
            )
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