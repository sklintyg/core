package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;


public class FK7211PdfGenerator {

  private static final String PATIENT_NAME_FIELD_ID =
      "form1[0].#subform[0].flt_txtNormal[0]";
  private static final String PATIENT_ID_FIELD_ID =
      "form1[0].#subform[0].flt_pnr[0]";
  private static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID =
      "form1[0].#subform[0].flt_dat[0]";
  private static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[0]";
  private static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[1]";
  private static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[2]";
  private static final String SIGNATURE_DATE_FIELD_ID =
      "form1[0].#subform[0].flt_datUnderskrift[0]";
  private static final String SIGNATURE_FULL_NAME_FIELD_ID =
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]";
  private static final String SIGNATURE_PA_TITLE_FIELD_ID =
      "form1[0].#subform[0].flt_txtBefattning[0]";
  private static final String SIGNATURE_SPECIALITY_FIELD_ID =
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]";
  private static final String SIGNATURE_HSA_ID_FIELD_ID =
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]";
  private static final String SIGNATURE_WORKPLACE_CODE_FIELD_ID =
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]";
  private static final String SIGNATURE_CERTIFIER_PERSON_ID_FIELD_ID =
      "form1[0].#subform[0].flt_txtPersonNrLakare[0]";
  private static final String SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID =
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]";
  private static final String CHECKED_BOX_VALUE = "1";
  private static final String UNCHECKED_BOX_VALUE = "Off";

  private static final String DIGITALLY_SIGNED =
      "Detta är en utskrift av ett elektroniskt intyg. "
          + "Intyget har signerats elektroniskt av intygsutfärdaren.";

  private final PDDocument doc;
  private final PDAcroForm acroForm;
  private final Certificate certificate;

  public FK7211PdfGenerator(Certificate certificate) throws IOException {
    this.certificate = certificate;
    String template = "fk7211_v1.pdf";
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(template);
    if (inputStream == null) {
      throw new IllegalArgumentException("Template not found: " + template);
    }
    doc = Loader.loadPDF(inputStream.readAllBytes());
    final var documentCatalog = doc.getDocumentCatalog();
    acroForm = documentCatalog.getAcroForm();

    setPatientInformation();
    setExpectedDeliveryDate();
    setIssuerRole();
    setSignedDate();
    setIssuerFullName();
    setPaTitles();
    setSpeciality();
    setHsaId();
    setWorkplaceCode();
    setContactInformation();

    inputStream.close();
  }

  private void setPatientInformation()
      throws IOException {
    final var patientNameField = acroForm.getField(PATIENT_NAME_FIELD_ID);
    patientNameField.setValue(certificate.certificateMetaData().patient().name().fullName());

    final var patientIdField = acroForm.getField(PATIENT_ID_FIELD_ID);
    patientIdField.setValue(certificate.certificateMetaData().patient().id().id());
  }

  private void setExpectedDeliveryDate()
      throws IOException {
    final var expectedDeliveryDate = acroForm.getField(
        QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);
    final var dateValue = certificate.elementData().get(0).value();

    if (dateValue instanceof ElementValueDate elementValueDate) {
      expectedDeliveryDate.setValue((elementValueDate).date().toString());
    }
  }

  private void setIssuerRole() throws IOException {
    //Kolla upp hur göra med vårdadmin
    final var role = certificate.certificateMetaData().issuer().role();

    switch (role) {
      case DOCTOR:
        final var certifierDoctor = acroForm.getField(
            QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID);
        certifierDoctor.setValue(CHECKED_BOX_VALUE);
        break;
      case MIDWIFE:
        final var certifierMidwife = acroForm.getField(
            QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID);
        certifierMidwife.setValue(CHECKED_BOX_VALUE);
        break;
      case NURSE:
        final var certifierNurse = acroForm.getField(
            QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID);
        certifierNurse.setValue(CHECKED_BOX_VALUE);
        break;
      default:
        break;
    }
  }

  private void setSignedDate() throws IOException {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    signedDate.setValue(certificate.signed().format(DateTimeFormatter.ISO_DATE));
  }

  private void setIssuerFullName() throws IOException {
    final var signatureFullName = acroForm.getField(SIGNATURE_FULL_NAME_FIELD_ID);
    signatureFullName.setValue(certificate.certificateMetaData().issuer().name().fullName());
  }

  private void setPaTitles() throws IOException {
    final var paTitles = certificate.certificateMetaData().issuer().paTitles();
    if (paTitles != null) {

      final var paTitleCodes = paTitles.stream().map(PaTitle::code)
          .collect(Collectors.joining(", "));

      final var signaturePaTitles = acroForm.getField(SIGNATURE_PA_TITLE_FIELD_ID);
      signaturePaTitles.setValue(paTitleCodes);
    }
  }

  private void setSpeciality() throws IOException {
    final var specialities = certificate.certificateMetaData().issuer().specialities();
    if (specialities != null) {

      final var mappedSpecialities = specialities.stream().map(Speciality::value)
          .collect(Collectors.joining(", "));

      final var signaturePaTitles = acroForm.getField(SIGNATURE_SPECIALITY_FIELD_ID);
      signaturePaTitles.setValue(mappedSpecialities);
    }
  }

  private void setHsaId() throws IOException {
    final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
    if (hsaId != null) {
      final var signatureHsaId = acroForm.getField(SIGNATURE_HSA_ID_FIELD_ID);
      signatureHsaId.setValue(hsaId);
    }
  }

  private void setWorkplaceCode() throws IOException {
    final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode()
        .code();
    if (workplaceCode != null) {
      final var signatureWorkplaceCode = acroForm.getField(SIGNATURE_WORKPLACE_CODE_FIELD_ID);
      signatureWorkplaceCode.setValue(workplaceCode);
    }
  }

  private void setContactInformation()
      throws IOException {

    final var unitName = certificate.certificateMetaData().issuingUnit().name().name();
    final var address = certificate.certificateMetaData().issuingUnit().address().address();
    final var zipCode = certificate.certificateMetaData().issuingUnit().address().zipCode();
    final var city = certificate.certificateMetaData().issuingUnit().address().city();
    final var phoneNumber = certificate.certificateMetaData().issuingUnit().contactInfo()
        .phoneNumber();

    final var contactInfo = String.format("%s%n%s%n%s %s%nTelefon: %s", unitName, address, zipCode,
        city, phoneNumber).replaceAll("\\r\\n|\\r|\\n", "\n");

    final var contactInformation = acroForm.getField(
        SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID);
    contactInformation.setValue(contactInfo);
  }

  public PDDocument getDocument() {
    return doc;
  }

}
