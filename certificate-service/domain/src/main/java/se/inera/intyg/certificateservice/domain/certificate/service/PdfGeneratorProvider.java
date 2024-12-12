package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class PdfGeneratorProvider {

  @Qualifier("certificatePdfService")
  private PdfGenerator certificatePdfService;
  @Qualifier("generalPdfService")
  private PdfGenerator generalPdfService;

  public static PdfGenerator provider(Certificate certificate) {
    if (certificate.certificateModel().pdfSpecification() != null) {
      //return new CertificatePdfGenerator();
    }

    return null;
  }

}
