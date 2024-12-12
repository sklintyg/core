package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class PdfGeneratorProvider {

  private final PdfGenerator certificatePdfService;
  private final PdfGenerator generalPdfService;

  public PdfGeneratorProvider(PdfGenerator certificatePdfService, PdfGenerator generalPdfService) {
    this.certificatePdfService = certificatePdfService;
    this.generalPdfService = generalPdfService;
  }

  public PdfGenerator provider(Certificate certificate) {
    if (certificate.certificateModel().pdfSpecification() != null) {
      return certificatePdfService;
    }

    return generalPdfService;
  }

}
