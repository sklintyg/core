package se.inera.intyg.certificateservice.pdfboxgenerator;

import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.DIGITALLY_SIGNED_TEXT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_FULL_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_SPECIALITY_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_WORKPLACE_CODE_FIELD_ID;

import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;

public class PdfGeneratorSignatureToolkit {

  private PdfGeneratorSignatureToolkit() {
    throw new IllegalStateException("Utility class!");
  }

  public static void setSignedValues(PDDocument document, PDAcroForm acroForm,
      Certificate certificate)
      throws IOException {
    setDigitalSignatureText(document, acroForm);
    setSignedDate(acroForm, certificate);
    setIssuerFullName(acroForm, certificate);
    setPaTitles(acroForm, certificate);
    setSpeciality(acroForm, certificate);
    setHsaId(acroForm, certificate);
    setWorkplaceCode(acroForm, certificate);
  }

  private static void setSignedDate(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    PdfGeneratorValueToolkit.setValue(acroForm,
        SIGNATURE_DATE_FIELD_ID,
        certificate.signed().format(DateTimeFormatter.ISO_DATE)
    );
  }

  private static void setIssuerFullName(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    PdfGeneratorValueToolkit.setValue(acroForm, SIGNATURE_FULL_NAME_FIELD_ID,
        certificate.certificateMetaData().issuer().name().fullName());
  }

  private static void setPaTitles(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var paTitles = certificate.certificateMetaData().issuer().paTitles();
    if (paTitles != null) {
      final var paTitleCodes = paTitles.stream()
          .map(PaTitle::code)
          .collect(Collectors.joining(", "));

      PdfGeneratorValueToolkit.setValue(acroForm, SIGNATURE_PA_TITLE_FIELD_ID, paTitleCodes);
    }
  }

  private static void setSpeciality(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    final var specialities = certificate.certificateMetaData().issuer().specialities();
    if (specialities != null) {
      final var mappedSpecialities = specialities.stream().map(Speciality::value)
          .collect(Collectors.joining(", "));

      PdfGeneratorValueToolkit.setValue(
          acroForm,
          SIGNATURE_SPECIALITY_FIELD_ID,
          mappedSpecialities);
    }
  }

  private static void setHsaId(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
    PdfGeneratorValueToolkit.setValue(
        acroForm,
        SIGNATURE_HSA_ID_FIELD_ID,
        hsaId
    );
  }

  private static void setWorkplaceCode(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode();

    if (workplaceCode == null) {
      return;
    }

    PdfGeneratorValueToolkit.setValue(
        acroForm,
        SIGNATURE_WORKPLACE_CODE_FIELD_ID,
        workplaceCode.code()
    );
  }

  private static void setDigitalSignatureText(PDDocument pdDocument, PDAcroForm acroForm)
      throws IOException {
    PdfGeneratorTextToolkit.addText(
        pdDocument,
        DIGITALLY_SIGNED_TEXT,
        8,
        null,
        Color.gray,
        getSignatureOffsetX(acroForm),
        getSignatureOffsetY(acroForm),
        true
    );
  }

  private static float getSignatureOffsetY(PDAcroForm acroForm) {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    final var rectangle = signedDate.getWidgets().get(0).getRectangle();
    return rectangle.getLowerLeftY();
  }

  private static float getSignatureOffsetX(PDAcroForm acroForm) {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    final var rectangle = signedDate.getWidgets().get(0).getRectangle();
    return rectangle.getUpperRightX();
  }
}
