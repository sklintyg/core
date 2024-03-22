package se.inera.intyg.certificateservice.pdfboxgenerator;

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
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_SIGNATURE_POSITION_X;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_SIGNATURE_POSITION_Y;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_SPECIALITY_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.SIGNATURE_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.FK7211PdfConstants.WATERMARK_DRAFT;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.util.Matrix;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;

public class CertificatePdfGenerator implements PdfGenerator {

  public final ClassLoader classLoader = getClass().getClassLoader();

  public Pdf generate(Certificate certificate) {

    try {
      File pdfTemplate = new File(classLoader.getResource(
          certificate.certificateModel().pdfTemplatePath()).getFile());
      PDDocument fk7211Pdf = Loader.loadPDF(pdfTemplate);

      final var documentCatalog = fk7211Pdf.getDocumentCatalog();
      final var acroForm = documentCatalog.getAcroForm();
      final var page = fk7211Pdf.getPage(0);

      setPatientInformation(acroForm, certificate);
      setExpectedDeliveryDate(acroForm, certificate);
      setIssuerRole(acroForm, certificate);
      setContactInformation(acroForm, certificate);

      if (certificate.status() == Status.SIGNED) {
        setSignedDate(acroForm, certificate);
        setDigitalSignatureText(fk7211Pdf, page);
        setIssuerFullName(acroForm, certificate);
        setPaTitles(acroForm, certificate);
        setSpeciality(acroForm, certificate);
        setHsaId(acroForm, certificate);
        setWorkplaceCode(acroForm, certificate);
        setMarginText(fk7211Pdf, page, certificate);
      }

      if (certificate.status() == Status.DRAFT) {
        setDraftWatermark(fk7211Pdf, page);
      }

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      fk7211Pdf.save(byteArrayOutputStream);
      fk7211Pdf.save("fk7211_test.pdf");
      fk7211Pdf.close();
      final var fileName = setFileName(certificate);
      return new Pdf(byteArrayOutputStream.toByteArray(), fileName);

    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf");
    }
  }

  private void setPatientInformation(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var patientNameField = acroForm.getField(PATIENT_NAME_FIELD_ID);
      patientNameField.setValue(certificate.certificateMetaData().patient().name().fullName());

      final var patientIdField = acroForm.getField(PATIENT_ID_FIELD_ID);
      patientIdField.setValue(certificate.certificateMetaData().patient().id().id());

    } catch (Exception e) {
      throw new IllegalStateException("Could not set patient information");
    }
  }

  private void setExpectedDeliveryDate(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var expectedDeliveryDate = acroForm.getField(
          QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID);

      if (!certificate.elementData().isEmpty()) {
        final var dateValue = certificate.elementData().get(0).value();

        if (dateValue instanceof ElementValueDate elementValueDate) {
          expectedDeliveryDate.setValue((elementValueDate).date().toString());
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException("Could not set expected delivery date");
    }
  }

  private void setIssuerRole(PDAcroForm acroForm, Certificate certificate) {
    try {
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
    } catch (Exception e) {
      throw new IllegalStateException("Could not set issuer role");
    }
  }

  private void setSignedDate(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
      signedDate.setValue(certificate.signed().format(DateTimeFormatter.ISO_DATE));
    } catch (Exception e) {
      throw new IllegalStateException("Could not set signed date");
    }
  }

  private void setDigitalSignatureText(PDDocument fk7211Pdf, PDPage page) {
    try {
      final var contentStream = new PDPageContentStream(fk7211Pdf, page, AppendMode.APPEND,
          true, true);
      contentStream.beginText();
      contentStream.newLineAtOffset(SIGNATURE_SIGNATURE_POSITION_X, SIGNATURE_SIGNATURE_POSITION_Y);
      contentStream.setNonStrokingColor(Color.gray);
      contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 8);
      contentStream.showText(DIGITALLY_SIGNED_TEXT);
      contentStream.endText();
      contentStream.close();

    } catch (Exception e) {
      throw new IllegalStateException("Could not set digital signature text ");
    }
  }

  private void setIssuerFullName(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var signatureFullName = acroForm.getField(SIGNATURE_FULL_NAME_FIELD_ID);
      signatureFullName.setValue(certificate.certificateMetaData().issuer().name().fullName());

    } catch (Exception e) {
      throw new IllegalStateException("Could not set issuer name");
    }
  }

  private void setPaTitles(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var paTitles = certificate.certificateMetaData().issuer().paTitles();
      if (paTitles != null) {

        final var paTitleCodes = paTitles.stream().map(PaTitle::code)
            .collect(Collectors.joining(", "));

        final var signaturePaTitles = acroForm.getField(SIGNATURE_PA_TITLE_FIELD_ID);
        signaturePaTitles.setValue(paTitleCodes);
      }

    } catch (Exception e) {
      throw new IllegalStateException("Could not set pa-titles");
    }
  }

  private void setSpeciality(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var specialities = certificate.certificateMetaData().issuer().specialities();
      if (specialities != null) {

        final var mappedSpecialities = specialities.stream().map(Speciality::value)
            .collect(Collectors.joining(", "));

        final var signaturePaTitles = acroForm.getField(SIGNATURE_SPECIALITY_FIELD_ID);
        signaturePaTitles.setValue(mappedSpecialities);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Could not set speciality");
    }
  }

  private void setHsaId(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
      if (hsaId != null) {
        final var signatureHsaId = acroForm.getField(SIGNATURE_HSA_ID_FIELD_ID);
        signatureHsaId.setValue(hsaId);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Could not set HSA-id ");
    }
  }

  private void setWorkplaceCode(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode()
          .code();
      if (workplaceCode != null) {
        final var signatureWorkplaceCode = acroForm.getField(SIGNATURE_WORKPLACE_CODE_FIELD_ID);
        signatureWorkplaceCode.setValue(workplaceCode);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Could not set workplace code");
    }
  }

  private void setContactInformation(PDAcroForm acroForm, Certificate certificate) {
    try {
      final var unitName = certificate.certificateMetaData().issuingUnit().name().name();
      final var address = certificate.certificateMetaData().issuingUnit().address().address();
      final var zipCode = certificate.certificateMetaData().issuingUnit().address().zipCode();
      final var city = certificate.certificateMetaData().issuingUnit().address().city();
      final var phoneNumber = certificate.certificateMetaData().issuingUnit().contactInfo()
          .phoneNumber();

      final var contactInfo = String.format("%s%n%s%n%s %s%nTelefon: %s", unitName, address,
          zipCode,
          city, phoneNumber).replaceAll("\\r\\n|\\r|\\n", "\n");

      final var contactInformation = acroForm.getField(
          SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID);
      contactInformation.setValue(contactInfo);

    } catch (Exception e) {
      throw new IllegalStateException("Could not set contact information");
    }
  }

  private void setMarginText(PDDocument fk7211Pdf, PDPage page, Certificate certificate) {
    try {
      final var contentStream = new PDPageContentStream(fk7211Pdf, page, AppendMode.APPEND,
          true, true);

      final var certificateId = certificate.id().id();
      contentStream.transform(Matrix.getRotateInstance(Math.PI / 2, 607, 280));
      contentStream.beginText();
      contentStream.newLineAtOffset(30, 30);
      contentStream.setNonStrokingColor(Color.black);
      contentStream.setFont(new PDType1Font(FontName.HELVETICA), 8);
      contentStream.showText(
          String.format("Intygsid: %s. Intyget är utskrivet från Webcert", certificateId));
      contentStream.endText();
      contentStream.close();

    } catch (Exception e) {
      throw new IllegalStateException("Could not set margin text");
    }
  }

  private void setDraftWatermark(PDDocument fk7211Pdf, PDPage page) {
    try {
      final var contentStream = new PDPageContentStream(fk7211Pdf, page, AppendMode.APPEND,
          true, true);

      final var fontHeight = 105;
      final var font = new PDType1Font(FontName.HELVETICA);

      final var width = page.getMediaBox().getWidth();
      float height = page.getMediaBox().getHeight();

      final var stringWidth = font.getStringWidth(WATERMARK_DRAFT) / 1000 * fontHeight;
      final var diagonalLength = (float) Math.sqrt(width * width + height * height);
      final var angle = (float) Math.atan2(height, width);
      final var x = (diagonalLength - stringWidth) / 2;
      final var y = -fontHeight / 4;

      contentStream.transform(Matrix.getRotateInstance(angle, 0, 0));
      contentStream.setFont(font, fontHeight);

      PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
      gs.setNonStrokingAlphaConstant(0.5f);
      gs.setStrokingAlphaConstant(0.5f);
      contentStream.setGraphicsStateParameters(gs);
      contentStream.setNonStrokingColor(Color.gray);

      contentStream.beginText();
      contentStream.newLineAtOffset(x, y);
      contentStream.showText(WATERMARK_DRAFT);
      contentStream.endText();
      contentStream.close();

    } catch (Exception e) {
      throw new IllegalStateException("Could not set draft water mark ");
    }
  }

  private String setFileName(Certificate certificate) {
    try {
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

    } catch (Exception e) {
      throw new IllegalStateException("Could not set file name ");
    }
  }
}
