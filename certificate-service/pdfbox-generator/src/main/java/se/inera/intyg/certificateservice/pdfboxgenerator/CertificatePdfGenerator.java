package se.inera.intyg.certificateservice.pdfboxgenerator;

import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.DIGITALLY_SIGNED;
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

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.util.Matrix;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;

public class CertificatePdfGenerator implements PdfGenerator {

  public final ClassLoader classLoader = getClass().getClassLoader();

  public Pdf generate(Certificate certificate) throws IOException {

    File pdfTemplate = new File(classLoader.getResource(
        certificate.certificateModel().pdfTemplatePath()).getFile());

    PDDocument fk7211Pdf = Loader.loadPDF(pdfTemplate);

    final var documentCatalog = fk7211Pdf.getDocumentCatalog();
    final var acroForm = documentCatalog.getAcroForm();
    final var page = fk7211Pdf.getPage(0);
    final var contentStream = new PDPageContentStream(fk7211Pdf, page, AppendMode.APPEND,
        true, true);

    setPatientInformation(acroForm, certificate);
    setExpectedDeliveryDate(acroForm, certificate);
    setIssuerRole(acroForm, certificate);
    setSignedDate(acroForm, certificate);
    setDigitalSignatureText(contentStream);
    setIssuerFullName(acroForm, certificate);
    setPaTitles(acroForm, certificate);
    setSpeciality(acroForm, certificate);
    setHsaId(acroForm, certificate);
    setWorkplaceCode(acroForm, certificate);
    setContactInformation(acroForm, certificate);
    setMarginText(contentStream, certificate);
    //setWatermark(fk7211pdf); olika för olika statusar? hur noga är det att den ser lika ut?
    //Personnummer - saknas i Staff, hur göra? PL får "fejk"-hsa från WC?

    contentStream.endText();
    contentStream.close();
    //acroForm.flatten();

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    fk7211Pdf.save(byteArrayOutputStream);
    fk7211Pdf.save("fk7211_test.pdf");
    fk7211Pdf.close();

    return new Pdf(byteArrayOutputStream.toByteArray(), setFileName(certificate));
  }

  private void setPatientInformation(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    final var patientNameField = acroForm.getField(PATIENT_NAME_FIELD_ID);
    patientNameField.setValue(certificate.certificateMetaData().patient().name().fullName());

    final var patientIdField = acroForm.getField(PATIENT_ID_FIELD_ID);
    patientIdField.setValue(certificate.certificateMetaData().patient().id().id());
  }

  private void setExpectedDeliveryDate(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    final var expectedDeliveryDate = acroForm.getField(
        QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);
    final var dateValue = certificate.elementData().get(0).value();

    if (dateValue instanceof ElementValueDate elementValueDate) {
      expectedDeliveryDate.setValue((elementValueDate).date().toString());
    }
  }

  private void setIssuerRole(PDAcroForm acroForm, Certificate certificate) throws IOException {
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

  private void setSignedDate(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    signedDate.setValue(certificate.signed().format(DateTimeFormatter.ISO_DATE));
  }

  private void setDigitalSignatureText(PDPageContentStream contentStream) throws IOException {
    contentStream.beginText();
    contentStream.newLineAtOffset(171, 522);
    contentStream.setNonStrokingColor(Color.gray);
    contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 8);
    contentStream.showText(DIGITALLY_SIGNED);
    contentStream.endText();
  }

  private void setIssuerFullName(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var signatureFullName = acroForm.getField(SIGNATURE_FULL_NAME_FIELD_ID);
    signatureFullName.setValue(certificate.certificateMetaData().issuer().name().fullName());
  }

  private void setPaTitles(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var paTitles = certificate.certificateMetaData().issuer().paTitles();
    if (paTitles != null) {

      final var paTitleCodes = paTitles.stream().map(PaTitle::code)
          .collect(Collectors.joining(", "));

      final var signaturePaTitles = acroForm.getField(SIGNATURE_PA_TITLE_FIELD_ID);
      signaturePaTitles.setValue(paTitleCodes);
    }
  }

  private void setSpeciality(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var specialities = certificate.certificateMetaData().issuer().specialities();
    if (specialities != null) {

      final var mappedSpecialities = specialities.stream().map(Speciality::value)
          .collect(Collectors.joining(", "));

      final var signaturePaTitles = acroForm.getField(SIGNATURE_SPECIALITY_FIELD_ID);
      signaturePaTitles.setValue(mappedSpecialities);
    }
  }

  private void setHsaId(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
    if (hsaId != null) {
      final var signatureHsaId = acroForm.getField(SIGNATURE_HSA_ID_FIELD_ID);
      signatureHsaId.setValue(hsaId);
    }
  }

  private void setWorkplaceCode(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode()
        .code();
    if (workplaceCode != null) {
      final var signatureWorkplaceCode = acroForm.getField(SIGNATURE_WORKPLACE_CODE_FIELD_ID);
      signatureWorkplaceCode.setValue(workplaceCode);
    }
  }

  private void setContactInformation(PDAcroForm acroForm, Certificate certificate)
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

  private void setMarginText(PDPageContentStream contentStream, Certificate certificate)
      throws IOException {
    final var certificateId = certificate.id().id();
    contentStream.transform(Matrix.getRotateInstance(Math.PI / 2, 607, 280));
    contentStream.beginText();
    contentStream.newLineAtOffset(30, 30);
    contentStream.setNonStrokingColor(Color.black);
    contentStream.setFont(new PDType1Font(FontName.HELVETICA), 8);
    contentStream.showText(
        String.format("Intygsid: %s. Intyget är utskrivet från Webcert", certificateId));
  }

  private void setWatermark(PDDocument fk7211pdf) {

  }

  private String setFileName(Certificate certificate) {
    final var certificateName = certificate.certificateModel().name();
    final var timestamp = LocalDateTime.now()
        .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

    return String.format("%s_%s", certificateName, timestamp)
        .replace("å", "a")
        .replace("ä", "a")
        .replace("ö", "o")
        .replace(" ", "_")
        .replace("–", "")
        .replace("__", "_")
        .toLowerCase();
  }
}
