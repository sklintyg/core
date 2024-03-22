package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ANNA_SJUKSKOTERSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.PATIENT_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_FULL_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_SPECIALITY_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_WORKPLACE_CODE_FIELD_ID;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final LocalDateTime SIGNED_DATE = LocalDateTime.now();
  private static final LocalDate DELIVERY_DATE = LocalDate.now();
  private static final String CHECKED_BOX_VALUE = "1";
  private static final String UNCHECKED_BOX_VALUE = "Off";

  @InjectMocks
  CertificatePdfGenerator certificatePdfGenerator;

  @BeforeEach
  void setUp() {
    buildCertificate(AJLA_DOKTOR);
  }

  @Nested
  class PatientInfo {

    @Test
    void shouldSetPatientName() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR).certificateMetaData().patient().name()
          .fullName();

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var field = getPdfField(pdfByteArray, PATIENT_NAME_FIELD_ID);

      assertEquals(expected, field.getValueAsString());
    }

    @Test
    void shouldSetPatientId() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR).certificateMetaData().patient().id().id();

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var field = getPdfField(pdfByteArray, PATIENT_ID_FIELD_ID);

      assertEquals(expected, field.getValueAsString());
    }
  }

  @Nested
  class PdfData {

    @Test
    void shouldSetExpectedDeliveryDate() throws IOException {
      final var pdfResponse = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var field = getPdfField(pdfResponse,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

      assertEquals(DELIVERY_DATE.toString(), field.getValueAsString());
    }
  }

  @Nested
  class SignatureInfo {

    @Test
    void shouldOnlySetDoctorAsCertifierIfIssuerIsDoctor() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var field = getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldOnlySetMidwifeAsCertifierIfIssuerIsMidwife() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(BARNMORSKA));
      final var field = getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldOnlySetNurseAsCertifierIfIssuerIsNurse() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(ANNA_SJUKSKOTERSKA));
      final var field = getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldSetSignatureDate() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR).signed()
          .format(DateTimeFormatter.ISO_DATE);

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_DATE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetSignatureFullName() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR)
          .certificateMetaData().issuer().name().fullName();

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_FULL_NAME_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetPaTitlesIfNotNull() throws IOException {
      final var expected = "203090, 601010";

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_PA_TITLE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetSpecialityIfNotNull() throws IOException {
      final var expected = "Allmänmedicin, Psykiatri";

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_SPECIALITY_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetHsaIdIfNotNull() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR).certificateMetaData().issuer().hsaId()
          .id();

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_HSA_ID_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetWorkplaceCodeIfNotNull() throws IOException {
      final var expected = "1627";

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_WORKPLACE_CODE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetContactInfo() throws IOException {
      final var expected = """
          Alfa Allergimottagningen
          Storgatan 1
          12345 Småmåla
          Telefon: 0101234567890""";

      final var pdfByteArray = certificatePdfGenerator.generate(buildCertificate(AJLA_DOKTOR));
      final var selectedField = getPdfField(pdfByteArray,
          SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

  }

  @Nested
  class ManuallySetValues {

//    @Test
//    void shouldSetFileNameCorrectly() {
//      final var expected =
//    }
  }

  private PDField getPdfField(Pdf pdfByteArray, String fieldId) throws IOException {
    PDDocument fk7211Pdf = Loader.loadPDF(pdfByteArray.pdfData());
    final var documentCatalog = fk7211Pdf.getDocumentCatalog();
    final var acroForm = documentCatalog.getAcroForm();
    return acroForm.getField(fieldId);
  }

  private Certificate buildCertificate(Staff staff) {
    return fk7211CertificateBuilder()
        .certificateMetaData(CertificateMetaData.builder()
            .issuer(staff)
            .patient(ATHENA_REACT_ANDERSSON)
            .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careUnit(ALFA_MEDICINCENTRUM)
            .careProvider(ALFA_REGIONEN)
            .build())
        .elementData(List.of(DATE))
        .certificateModel(FK7211_CERTIFICATE_MODEL)
        .signed(SIGNED_DATE)
        .build();
  }

}