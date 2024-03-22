package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ANNA_SJUKSKOTERSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.DIGITALLY_SIGNED_TEXT;
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
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.UNCHECKED_BOX_VALUE;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final LocalDateTime SIGNED_DATE = LocalDateTime.now();
  private static final LocalDate DELIVERY_DATE = LocalDate.now();

  @InjectMocks
  CertificatePdfGenerator certificatePdfGenerator;

  @Nested
  class PatientInfo {

    @Test
    void shouldSetPatientName() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).certificateMetaData()
          .patient().name().fullName();

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var field = getPdfField(pdfByteArray, PATIENT_NAME_FIELD_ID);

      assertEquals(expected, field.getValueAsString());
    }

    @Test
    void shouldSetPatientId() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).certificateMetaData()
          .patient().id().id();

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var field = getPdfField(pdfByteArray, PATIENT_ID_FIELD_ID);

      assertEquals(expected, field.getValueAsString());
    }
  }

  @Nested
  class PdfData {

    @Test
    void shouldSetExpectedDeliveryDateIfDateIsProvided() throws IOException {
      final var pdfResponse = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var field = getPdfField(pdfResponse,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

      assertEquals(DELIVERY_DATE.toString(), field.getValueAsString());
    }

    @Test
    void shouldNotSetExpectedDeliveryDateIfDateIsNotProvided() throws IOException {
      final var pdfResponse = certificatePdfGenerator.generate(
          buildCertificateWithoutData());

      final var field = getPdfField(pdfResponse,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

      assertEquals("", field.getValueAsString());
    }

    @Test
    void shouldOnlySetDoctorAsCertifierIfIssuerIsDoctor() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
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
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(BARNMORSKA, Status.SIGNED));
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
          buildCertificate(ANNA_SJUKSKOTERSKA, Status.SIGNED));
      final var field = getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldNotSetCertifierIfRoleIsAdmin() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(ALVA_VARDADMINISTRATOR, Status.SIGNED));

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(pdfByteArray,
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }
  }

  @Nested
  class Signed {

    @Test
    void shouldSetSignatureDateIfCertificateIsSigned() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).signed()
          .format(DateTimeFormatter.ISO_DATE);
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_DATE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetSignatureFullNameIfCertificateIsSigned() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED)
          .certificateMetaData().issuer().name().fullName();

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_FULL_NAME_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetPaTitlesIfNotNullIfCertificateIsSigned() throws IOException {
      final var expected = "203090, 601010";

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_PA_TITLE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetSpecialityIfNotNullIfCertificateIsSigned() throws IOException {
      final var expected = "Allmänmedicin, Psykiatri";

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_SPECIALITY_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetHsaIdIfNotNullIfCertificateIsSigned() throws IOException {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).certificateMetaData()
          .issuer().hsaId()
          .id();

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_HSA_ID_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetWorkplaceCodeIfNotNullIfCertificateIsSigned() throws IOException {
      final var expected = "1627";

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_WORKPLACE_CODE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetContactInfoIfCertificateIsSigned() throws IOException {
      final var expected = """
          Alfa Allergimottagningen
          Storgatan 1
          12345 Småmåla
          Telefon: 0101234567890""";

      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var selectedField = getPdfField(pdfByteArray,
          SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }
  }

  @Nested
  class Draft {

    @Test
    void shouldNotSetSignatureDateIfCertificateIsDraft() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_DATE_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be empty but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetSignatureFullNameIfCertificateIsDraft() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_FULL_NAME_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be empty but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetPaTitlesIfCertificateIsDraft() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_PA_TITLE_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetSpecialityIfCertificateIsDraft() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_SPECIALITY_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetHsaIdIfCertificateIsDraft() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_HSA_ID_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetWorkplaceCodeIfCertificateIsDraft() throws IOException {
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));
      final var selectedField = getPdfField(pdfByteArray, SIGNATURE_WORKPLACE_CODE_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }
  }

  @Nested
  class ManuallySetValues {

    @Test
    void shouldSetCorrectFileName() {
      final var expected = "intyg_om_graviditet_" + LocalDateTime.now()
          .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));
      final var pdfByteArray = certificatePdfGenerator.generate(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      assertEquals(expected, pdfByteArray.fileName());
    }

    @Test
    void shouldAddDigitalSignatureTextIfCertificateIsSigned() throws IOException {
      buildCertificate(AJLA_DOKTOR, Status.SIGNED);
      final var expected = DIGITALLY_SIGNED_TEXT;
      PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile("fk7211_test.pdf"));
      PDFTextStripper textStripper = new PDFTextStripper();
      final var pdfText = textStripper.getText(document);

      assertTrue(pdfText.contains(expected),
          String.format("Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              expected, pdfText)
      );
    }
  }

  private PDField getPdfField(Pdf pdfByteArray, String fieldId) throws IOException {
    PDDocument fk7211Pdf = Loader.loadPDF(pdfByteArray.pdfData());
    final var documentCatalog = fk7211Pdf.getDocumentCatalog();
    final var acroForm = documentCatalog.getAcroForm();
    return acroForm.getField(fieldId);
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
        .elementData(List.of(DATE))
        .certificateModel(FK7211_CERTIFICATE_MODEL)
        .signed(SIGNED_DATE)
        .build();
  }

  private Certificate buildCertificateWithoutData() {
    return fk7211CertificateBuilder()
        .certificateMetaData(CertificateMetaData.builder()
            .issuer(AJLA_DOKTOR)
            .patient(ATHENA_REACT_ANDERSSON)
            .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careUnit(ALFA_MEDICINCENTRUM)
            .careProvider(ALFA_REGIONEN)
            .build())
        .status(Status.DRAFT)
        .elementData(Collections.emptyList())
        .certificateModel(FK7211_CERTIFICATE_MODEL)
        .signed(SIGNED_DATE)
        .build();
  }

}