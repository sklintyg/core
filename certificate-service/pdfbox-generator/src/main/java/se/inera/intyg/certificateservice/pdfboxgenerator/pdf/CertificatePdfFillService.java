package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_DATE_FIELD_ID;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfUnitValueGenerator;


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
      setFields(certificate, certificateValueGenerator, document);
      addTexts(certificate, additionalInfoText, certificateValueGenerator, document);

      return document;
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private void setFields(Certificate certificate,
      CertificateTypePdfFillService certificateValueGenerator, PDDocument document) {
    if (certificate.status() == Status.SIGNED) {
      setFieldValues(document, pdfSignatureValueGenerator.generate(certificate));
    }
    setFieldValues(document, certificateValueGenerator.getFields(certificate));
    setFieldValues(document, pdfUnitValueGenerator.generate(certificate));
    setFieldValues(document, pdfPatientValueGenerator.generate(certificate,
        certificateValueGenerator.getPatientIdFieldId()));
  }

  private void addTexts(Certificate certificate, String additionalInfoText,
      CertificateTypePdfFillService certificateValueGenerator, PDDocument document)
      throws IOException {
    setDraftWatermark(document, certificate, certificateValueGenerator);
    setSignatureText(
        document,
        certificate,
        certificateValueGenerator
    );
    setSentText(document, certificate, certificateValueGenerator);
    setMarginText(document, certificate, additionalInfoText, certificateValueGenerator);
  }

  private void setFieldValues(PDDocument document, List<PdfField> fields) {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    fields
        .stream()
        .filter(Objects::nonNull)
        .forEach(field -> {
          try {
            setFieldValue(acroForm, field.getId(), field.getValue());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void setSentText(PDDocument document, Certificate certificate,
      CertificateTypePdfFillService fillService)
      throws IOException {
    if (certificate.sent() != null && certificate.sent().sentAt() != null) {
      pdfAdditionalInformationTextGenerator.addSentText(document, certificate,
          fillService.getAvailableMcid());
      if (Boolean.TRUE.equals(certificate.certificateModel().availableForCitizen())) {
        pdfAdditionalInformationTextGenerator.addSentVisibilityText(document,
            fillService.getAvailableMcid());
      }
    }
  }

  private void setMarginText(PDDocument document, Certificate certificate,
      String additionalInfoText, CertificateTypePdfFillService pdfFillService)
      throws IOException {
    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addMarginAdditionalInfoText(
          document,
          certificate.id().id(),
          additionalInfoText,
          pdfFillService.getAvailableMcid()
      );
    }
  }

  private void setSignatureText(PDDocument document, Certificate certificate,
      CertificateTypePdfFillService pdfFillService)
      throws IOException {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addDigitalSignatureText(
          document, getSignatureOffsetX(acroForm), getSignatureOffsetY(acroForm),
          pdfFillService.getAvailableMcid(), pdfFillService.getSignatureTagIndex()
      );
    }
  }

  private void setDraftWatermark(PDDocument document, Certificate certificate,
      CertificateTypePdfFillService pdfFillService)
      throws IOException {
    if (certificate.status() == Status.DRAFT) {
      pdfAdditionalInformationTextGenerator.addDraftWatermark(document,
          pdfFillService.getAvailableMcid());
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
