package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfPatientInformationHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfSignatureHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfTextInformationHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfUnitInformationHelper;


public class CertificatePdfFillService {

  private final PdfUnitInformationHelper pdfUnitInformationHelper;
  private final PdfPatientInformationHelper pdfPatientInformationHelper;
  private final PdfSignatureHelper pdfSignatureHelper;
  private final PdfTextInformationHelper pdfTextInformationHelper;

  public CertificatePdfFillService(PdfUnitInformationHelper pdfUnitInformationHelper,
      PdfPatientInformationHelper pdfPatientInformationHelper,
      PdfSignatureHelper pdfSignatureHelper, PdfTextInformationHelper pdfTextInformationHelper) {
    this.pdfUnitInformationHelper = pdfUnitInformationHelper;
    this.pdfPatientInformationHelper = pdfPatientInformationHelper;
    this.pdfSignatureHelper = pdfSignatureHelper;
    this.pdfTextInformationHelper = pdfTextInformationHelper;
  }

  public PDDocument fillDocument(Certificate certificate, String additionalInfoText,
      PdfCertificateFillService certificateValueGenerator) {
    final var template = certificate.certificateModel().pdfTemplatePath();
    try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Template not found: " + template);
      }
      final var document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      final var acroForm = documentCatalog.getAcroForm();

      pdfPatientInformationHelper.setPatientInformation(
          acroForm,
          certificate,
          certificateValueGenerator.getPatientIdFormId()
      );

      pdfUnitInformationHelper.setContactInformation(acroForm, certificate);
      certificateValueGenerator.fillDocument(acroForm, certificate);

      setMarginText(document, certificate, additionalInfoText);
      setSentText(document, certificate);
      setDraftWatermark(document, certificate);

      if (certificate.status() == Status.SIGNED) {
        pdfSignatureHelper.setSignedValues(document, acroForm, certificate);
      }

      return document;
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private void setSentText(PDDocument document, Certificate certificate)
      throws IOException {
    if (certificate.sent() == null) {
      return;
    }

    pdfTextInformationHelper.addSentText(document, certificate);
    pdfTextInformationHelper.addSentVisibilityText(document);
  }

  private void setMarginText(PDDocument document, Certificate certificate,
      String additionalInfoText)
      throws IOException {

    if (certificate.status() != Status.SIGNED) {
      return;
    }

    pdfTextInformationHelper.addMarginAdditionalInfoText(
        document,
        certificate.id().id(),
        additionalInfoText
    );
  }

  private void setDraftWatermark(PDDocument document, Certificate certificate)
      throws IOException {
    if (certificate.status() != Status.DRAFT) {
      return;
    }

    pdfTextInformationHelper.addDraftWatermark(document);
  }
}
