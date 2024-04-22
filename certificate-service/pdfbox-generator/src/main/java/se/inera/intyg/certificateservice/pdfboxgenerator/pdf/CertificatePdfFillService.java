package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_DATE_FIELD_ID;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfUnitValueGenerator;


@Service
@RequiredArgsConstructor
public class CertificatePdfFillService {

  public static final int SIGNATURE_X_PADDING = 60;

  private final PdfUnitValueGenerator pdfUnitValueGenerator;
  private final PdfPatientValueGenerator pdfPatientValueGenerator;
  private final PdfSignatureValueGenerator pdfSignatureValueGenerator;
  private final PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;

  public PDDocument fillDocument(Certificate certificate, String additionalInfoText,
      CertificateTypePdfFillService certificateValueGenerator) {
    final var template = certificate.certificateModel().pdfTemplatePath();
    try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Template not found: " + template);
      }
      final var document = Loader.loadPDF(inputStream.readAllBytes());

      final var patientValues = pdfPatientValueGenerator.generate(
          certificate,
          certificateValueGenerator.getPatientIdFieldId()
      );
      final var unitValues = pdfUnitValueGenerator.generate(certificate);
      final var signatureValues = pdfSignatureValueGenerator.generate(certificate);
      final var certificateQuestionFields = certificateValueGenerator.getFields(certificate);

      setFieldValues(document, patientValues);
      setFieldValues(document, unitValues);
      setFieldValues(document, signatureValues);
      setFieldValues(document, certificateQuestionFields);

      setMarginText(document, certificate, additionalInfoText);
      setSentText(document, certificate);
      setDraftWatermark(document, certificate);
      setSignatureText(document, certificate);

      return document;
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private void setFieldValues(PDDocument document, List<PdfField> fields) {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    fields.stream()
        .forEach(field -> {
          try {
            setFieldValue(acroForm, field.getValue(), field.getId());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void setSentText(PDDocument document, Certificate certificate)
      throws IOException {
    if (certificate.sent() != null && certificate.sent().sentAt() != null) {
      pdfAdditionalInformationTextGenerator.addSentText(document, certificate);
      pdfAdditionalInformationTextGenerator.addSentVisibilityText(document);
    }
  }

  private void setMarginText(PDDocument document, Certificate certificate,
      String additionalInfoText)
      throws IOException {
    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addMarginAdditionalInfoText(
          document,
          certificate.id().id(),
          additionalInfoText
      );
    }
  }

  private void setSignatureText(PDDocument document, Certificate certificate)
      throws IOException {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addDigitalSignatureText(document, acroForm,
          getSignatureOffsetX(acroForm), getSignatureOffsetY(acroForm));
    }
  }

  private void setDraftWatermark(PDDocument document, Certificate certificate)
      throws IOException {
    if (certificate.status() == Status.DRAFT) {
      pdfAdditionalInformationTextGenerator.addDraftWatermark(document);
    }
  }

  private float getSignatureOffsetY(PDAcroForm acroForm) {
    final var rectangle = getSignedDateRectangle(acroForm);
    return rectangle.getLowerLeftY();
  }

  private float getSignatureOffsetX(PDAcroForm acroForm) {
    final var rectangle = getSignedDateRectangle(acroForm);
    return rectangle.getUpperRightX() + SIGNATURE_X_PADDING;
  }

  private static PDRectangle getSignedDateRectangle(PDAcroForm acroForm) {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    return signedDate.getWidgets().get(0).getRectangle();
  }

  private void setFieldValue(PDAcroForm acroForm, String fieldId, String value)
      throws IOException {
    if (value != null) {
      final var field = acroForm.getField(fieldId);
      validateField(fieldId, field);
      field.setValue(value);
    }
  }

  private static void validateField(String fieldId, PDField field) {
    if (field == null) {
      throw new IllegalStateException(
          String.format("Field is null when getting field of id '%s'", fieldId)
      );
    }
  }
}
