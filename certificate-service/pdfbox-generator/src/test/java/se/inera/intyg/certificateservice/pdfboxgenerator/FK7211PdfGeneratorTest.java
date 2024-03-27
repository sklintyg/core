package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ANNA_SJUKSKOTERSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.DIGITALLY_SIGNED_TEXT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_FULL_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_SPECIALITY_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.SIGNATURE_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfGenerator.UNCHECKED_BOX_VALUE;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class FK7211PdfGeneratorTest {

  private static final LocalDateTime SIGNED_DATE = LocalDateTime.now();
  private static final LocalDate DELIVERY_DATE = LocalDate.now();

  @Nested
  class PatientInfo {

    @Test
    void shouldSetPatientId() {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).certificateMetaData()
          .patient().id().id();

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var field = getPdfField(fk7211PdfGenerator.getDocument(), PATIENT_ID_FIELD_ID);

      assertEquals(expected, field.getValueAsString());
    }
  }

  @Nested
  class PdfData {

    @Test
    void shouldSetExpectedDeliveryDateIfDateIsProvided() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));
      final var field = getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

      assertEquals(DELIVERY_DATE.toString(), field.getValueAsString());
    }

    @Test
    void shouldNotSetExpectedDeliveryDateIfDateIsNotProvided() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificateWithoutData());

      final var field = getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

      assertEquals("", field.getValueAsString());
    }

    @Test
    void shouldOnlySetDoctorAsCertifierIfIssuerIsDoctor() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var field = getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldOnlySetMidwifeAsCertifierIfIssuerIsMidwife() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(BARNMORSKA, Status.SIGNED));

      final var field = getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldOnlySetNurseAsCertifierIfIssuerIsNurse() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(ANNA_SJUKSKOTERSKA, Status.SIGNED));

      final var field = getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID);

      assertEquals(CHECKED_BOX_VALUE, field.getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }

    @Test
    void shouldNotSetCertifierIfRoleIsAdmin() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(ALVA_VARDADMINISTRATOR, Status.SIGNED));

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID).getValueAsString());

      assertEquals(UNCHECKED_BOX_VALUE, getPdfField(fk7211PdfGenerator.getDocument(),
          QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID).getValueAsString());
    }
  }

  @Nested
  class Signed {

    @Test
    void shouldSetSignatureDateIfCertificateIsSigned() {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).signed()
          .format(DateTimeFormatter.ISO_DATE);

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_DATE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetSignatureFullNameIfCertificateIsSigned() {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED)
          .certificateMetaData().issuer().name().fullName();

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_FULL_NAME_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetPaTitlesIfNotNullIfCertificateIsSigned() {
      final var expected = "203090, 601010";

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_PA_TITLE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetSpecialityIfNotNullIfCertificateIsSigned() {
      final var expected = "Allmänmedicin, Psykiatri";

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_SPECIALITY_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetHsaIdIfNotNullIfCertificateIsSigned() {
      final var expected = buildCertificate(AJLA_DOKTOR, Status.SIGNED).certificateMetaData()
          .issuer().hsaId()
          .id();

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_HSA_ID_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetWorkplaceCodeIfNotNullIfCertificateIsSigned() {
      final var expected = "1627";

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_WORKPLACE_CODE_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldSetContactInfoIfCertificateIsSigned() {
      final var expected = """
          Alfa Allergimottagningen
          Storgatan 1
          12345 Småmåla
          Telefon: 0101234567890""";

      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID);

      assertEquals(expected, selectedField.getValueAsString());
    }

    @Test
    void shouldAddDigitalSignatureTextIfCertificateIsSigned() throws IOException {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.SIGNED));

      PDFTextStripper textStripper = new PDFTextStripper();
      final var pdfText = textStripper.getText(fk7211PdfGenerator.getDocument());

      assertTrue(pdfText.contains(DIGITALLY_SIGNED_TEXT),
          String.format("Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              DIGITALLY_SIGNED_TEXT, pdfText)
      );
    }
  }

  @Nested
  class Draft {

    @Test
    void shouldNotSetSignatureDateIfCertificateIsDraft() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_DATE_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be empty but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetSignatureFullNameIfCertificateIsDraft() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_FULL_NAME_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be empty but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetPaTitlesIfCertificateIsDraft() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_PA_TITLE_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetSpecialityIfCertificateIsDraft() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_SPECIALITY_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetHsaIdIfCertificateIsDraft() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_HSA_ID_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }

    @Test
    void shouldNotSetWorkplaceCodeIfCertificateIsDraft() {
      final var fk7211PdfGenerator = new FK7211PdfGenerator(
          buildCertificate(AJLA_DOKTOR, Status.DRAFT));

      final var selectedField = getPdfField(fk7211PdfGenerator.getDocument(),
          SIGNATURE_WORKPLACE_CODE_FIELD_ID);

      assertEquals("", selectedField.getValueAsString(),
          String.format("Expected field to be null but got '%s'", selectedField.getValueAsString())
      );
    }
  }

  private PDField getPdfField(PDDocument document, String fieldId) {
    final var documentCatalog = document.getDocumentCatalog();
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
        .elementData(List.of(DATE, CONTACT_INFO))
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