package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@RequiredArgsConstructor
public class PdfGeneratorProvider {

  private final PdfGenerator certificatePdfService;
  private final PdfGenerator generalPdfService;

  public PdfGenerator provider(Certificate certificate) {
    if (certificate.certificateModel().pdfSpecification() != null) {
      return certificatePdfService;
    }

    return generalPdfService;
  }

}
