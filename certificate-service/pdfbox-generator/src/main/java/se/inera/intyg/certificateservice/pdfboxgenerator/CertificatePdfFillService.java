package se.inera.intyg.certificateservice.pdfboxgenerator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;


public class CertificatePdfFillService {

  public PDDocument fillDocument(Certificate certificate,
      PdfCertificateFillService certificateValueGenerator) {
    final var template = certificate.certificateModel().pdfTemplatePath();
    try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Template not found: " + template);
      }
      final var pdDocument = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = pdDocument.getDocumentCatalog();
      final var acroForm = documentCatalog.getAcroForm();

      PdfGeneratorValueToolkit.setPatientInformation(
          acroForm,
          certificate,
          certificateValueGenerator.getPatientIdFormId()
      );
      PdfGeneratorValueToolkit.setContactInformation(acroForm, certificate);
      certificateValueGenerator.fillDocument(acroForm, certificate);

      if (certificate.status() == Status.SIGNED) {
        PdfGeneratorSignatureToolkit.setSignedValues(pdDocument, acroForm, certificate);
      }

      return pdDocument;
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }
}
