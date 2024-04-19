package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ANNA_SJUKSKOTERSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.util.PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.util.PdfConstants.UNCHECKED_BOX_VALUE;

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
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class FK7211PdfGeneratorTest {

  private static final LocalDate DELIVERY_DATE = LocalDate.now();
  private static final ElementData BERAKNAT_NEDKOMST_DATUM_ELEMENT_DATA = ElementData.builder()
      .id(QUESTION_BERAKNAT_NEDKOMSTDATUM_ID)
      .value(
          ElementValueDate.builder()
              .date(DELIVERY_DATE)
              .build()
      )
      .build();

  private PDAcroForm pdAcroForm;

  @InjectMocks
  private FK7211PdfGenerator fk7211PdfGenerator;

  @Test
  void shouldReturnPatientIdFormId() {
    assertEquals("form1[0].#subform[0].flt_pnr[0]", fk7211PdfGenerator.getPatientIdFormId());
  }

  @Test
  void shouldReturnGeneratorType() {
    assertEquals(
        fk7211CertificateBuilder().build().certificateModel().id().type(),
        fk7211PdfGenerator.getType()
    );
  }

  @Nested
  class PdfData {

    @BeforeEach
    void setup() throws IOException {
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      final var inputStream = classloader.getResourceAsStream("fk7211_v1.pdf");
      final var document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      pdAcroForm = documentCatalog.getAcroForm();
    }

    @Nested
    class ExpectedDeliveryDate {

      @Test
      void shouldSetExpectedDeliveryDateIfDateIsProvided() throws IOException {
        fk7211PdfGenerator.fillDocument(
            pdAcroForm,
            buildCertificate(List.of(BERAKNAT_NEDKOMST_DATUM_ELEMENT_DATA))
        );

        assertEquals(
            DELIVERY_DATE.toString(),
            pdAcroForm.getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID).getValueAsString()
        );
      }


      @Test
      void shouldNotSetExpectedDeliveryDateIfDateIsNotProvided() throws IOException {
        fk7211PdfGenerator.fillDocument(
            pdAcroForm,
            buildCertificate(Collections.emptyList())
        );

        assertEquals(
            "",
            pdAcroForm.getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID).getValueAsString()
        );
      }
    }

    @Nested
    class Issuer {

      @Test
      void shouldOnlySetDoctorAsCertifierIfIssuerIsDoctor() throws IOException {
        fk7211PdfGenerator.fillDocument(
            pdAcroForm,
            buildCertificate(AJLA_DOKTOR, Status.SIGNED)
        );

        assertAll(
            () -> assertEquals(
                CHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID)
                    .getValueAsString()
            )
        );
      }

      @Test
      void shouldOnlySetMidwifeAsCertifierIfIssuerIsMidwife() throws IOException {
        fk7211PdfGenerator.fillDocument(
            pdAcroForm,
            buildCertificate(BARNMORSKA, Status.SIGNED)
        );

        assertAll(
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                CHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID)
                    .getValueAsString()
            )
        );
      }

      @Test
      void shouldOnlySetNurseAsCertifierIfIssuerIsNurse() throws IOException {
        fk7211PdfGenerator.fillDocument(
            pdAcroForm,
            buildCertificate(ANNA_SJUKSKOTERSKA, Status.SIGNED)
        );

        assertAll(
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                CHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID)
                    .getValueAsString()
            )
        );
      }

      @Test
      void shouldNotSetCertifierIfRoleIsAdmin() throws IOException {
        fk7211PdfGenerator.fillDocument(
            pdAcroForm,
            buildCertificate(ALVA_VARDADMINISTRATOR, Status.SIGNED)
        );

        assertAll(
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID)
                    .getValueAsString()
            ),
            () -> assertEquals(
                UNCHECKED_BOX_VALUE,
                pdAcroForm
                    .getField(QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID)
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

  private Certificate buildCertificate(Staff staff, Status status) {
    return fk7211CertificateBuilder()
        .certificateMetaData(CertificateMetaData.builder()
            .issuer(staff)
            .patient(ATHENA_REACT_ANDERSSON)
            .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careUnit(ALFA_MEDICINCENTRUM)
            .careProvider(ALFA_REGIONEN)
            .build())
        .status(status)
        .build();
  }
}
