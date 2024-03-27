package se.inera.intyg.certificateservice.pdfboxgenerator;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;


public class FK7211PdfGenerator {

  public static final String PATIENT_NAME_FIELD_ID =
      "form1[0].#subform[0].flt_txtNormal[0]";
  public static final String PATIENT_ID_FIELD_ID =
      "form1[0].#subform[0].flt_pnr[0]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID =
      "form1[0].#subform[0].flt_dat[0]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[0]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[1]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[2]";
  public static final String SIGNATURE_DATE_FIELD_ID =
      "form1[0].#subform[0].flt_datUnderskrift[0]";
  public static final float SIGNATURE_SIGNATURE_POSITION_X = 171;
  public static final float SIGNATURE_SIGNATURE_POSITION_Y = 522;
  public static final String SIGNATURE_FULL_NAME_FIELD_ID =
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]";
  public static final String SIGNATURE_PA_TITLE_FIELD_ID =
      "form1[0].#subform[0].flt_txtBefattning[0]";
  public static final String SIGNATURE_SPECIALITY_FIELD_ID =
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]";
  public static final String SIGNATURE_HSA_ID_FIELD_ID =
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]";
  public static final String SIGNATURE_WORKPLACE_CODE_FIELD_ID =
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]";
  public static final String SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID =
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]";
  public static final String CHECKED_BOX_VALUE = "1";
  public static final String UNCHECKED_BOX_VALUE = "Off";
  public static final String DIGITALLY_SIGNED_TEXT =
      "Detta är en utskrift av ett elektroniskt intyg. "
          + "Intyget har signerats elektroniskt av intygsutfärdaren.";


  private final PDDocument pdDocument;
  private final PDAcroForm acroForm;

  public FK7211PdfGenerator(Certificate certificate) {
    final var template = certificate.certificateModel().pdfTemplatePath();
    try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Template not found: " + template);
      }
      pdDocument = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = pdDocument.getDocumentCatalog();
      acroForm = documentCatalog.getAcroForm();

      setPatientInformation(certificate);
      setExpectedDeliveryDate(certificate);
      setIssuerRole(certificate);
      setContactInformation(certificate);

      if (certificate.status() == Status.SIGNED) {
        setDigitalSignatureText();
        setSignedDate(certificate);
        setIssuerFullName(certificate);
        setPaTitles(certificate);
        setSpeciality(certificate);
        setHsaId(certificate);
        setWorkplaceCode(certificate);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private void setPatientInformation(Certificate certificate) throws IOException {
    final var patientIdField = acroForm.getField(PATIENT_ID_FIELD_ID);
    patientIdField.setValue(certificate.certificateMetaData().patient().id().id());
  }

  private void setExpectedDeliveryDate(Certificate certificate) throws IOException {
    final var expectedDeliveryDate = acroForm.getField(
        QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

    if (!certificate.elementData().isEmpty()) {
      final var dateValue = certificate.elementData().get(0).value();

      if (dateValue instanceof ElementValueDate elementValueDate) {
        expectedDeliveryDate.setValue((elementValueDate).date().toString());
      }
    }
  }

  private void setIssuerRole(Certificate certificate) throws IOException {
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

  private void setSignedDate(Certificate certificate) throws IOException {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    signedDate.setValue(certificate.signed().format(DateTimeFormatter.ISO_DATE));
  }

  private void setIssuerFullName(Certificate certificate) throws IOException {
    final var signatureFullName = acroForm.getField(SIGNATURE_FULL_NAME_FIELD_ID);
    signatureFullName.setValue(certificate.certificateMetaData().issuer().name().fullName());
  }

  private void setPaTitles(Certificate certificate) throws IOException {
    final var paTitles = certificate.certificateMetaData().issuer().paTitles();
    if (paTitles != null) {

      final var paTitleCodes = paTitles.stream().map(PaTitle::code)
          .collect(Collectors.joining(", "));

      final var signaturePaTitles = acroForm.getField(SIGNATURE_PA_TITLE_FIELD_ID);
      signaturePaTitles.setValue(paTitleCodes);
    }
  }

  private void setSpeciality(Certificate certificate) throws IOException {
    final var specialities = certificate.certificateMetaData().issuer().specialities();
    if (specialities != null) {

      final var mappedSpecialities = specialities.stream().map(Speciality::value)
          .collect(Collectors.joining(", "));

      final var signaturePaTitles = acroForm.getField(SIGNATURE_SPECIALITY_FIELD_ID);
      signaturePaTitles.setValue(mappedSpecialities);
    }
  }

  private void setHsaId(Certificate certificate) throws IOException {
    final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
    if (hsaId != null) {
      final var signatureHsaId = acroForm.getField(SIGNATURE_HSA_ID_FIELD_ID);
      signatureHsaId.setValue(hsaId);
    }
  }

  private void setWorkplaceCode(Certificate certificate) throws IOException {
    final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode()
        .code();
    if (workplaceCode != null) {
      final var signatureWorkplaceCode = acroForm.getField(SIGNATURE_WORKPLACE_CODE_FIELD_ID);
      signatureWorkplaceCode.setValue(workplaceCode);
    }
  }

  private void setContactInformation(Certificate certificate) throws IOException {
    final var elementData = certificate.elementData().stream()
        .filter(data -> data.id().equals(UNIT_CONTACT_INFORMATION))
        .findFirst();

    final var contactInfo = elementData.map(data -> {
      final var elementValue = (ElementValueUnitContactInformation) data.value();
      return certificate.certificateMetaData().issuingUnit().name().name() + "\n"
          + elementValue.address() + "\n"
          + elementValue.zipCode() + " " + elementValue.city() + "\n"
          + "Telefon: " + elementValue.phoneNumber();
    }).orElse("");

    final var contactInformation = acroForm.getField(
        SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID);
    contactInformation.setValue(contactInfo);
  }

  private void setDigitalSignatureText() throws IOException {
    final var contentStream = new PDPageContentStream(pdDocument, pdDocument.getPage(0),
        AppendMode.APPEND,
        true, true);
    contentStream.beginText();
    contentStream.newLineAtOffset(SIGNATURE_SIGNATURE_POSITION_X, SIGNATURE_SIGNATURE_POSITION_Y);
    contentStream.setNonStrokingColor(Color.gray);
    contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 8);
    contentStream.showText(DIGITALLY_SIGNED_TEXT);
    contentStream.endText();
    contentStream.close();
  }

  public PDDocument getDocument() {
    return pdDocument;
  }

}
