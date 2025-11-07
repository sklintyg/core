package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.GeneralPdfSpecification;

@RequiredArgsConstructor
public class PdfGeneratorProvider {

  private final PdfGenerator certificatePdfService;
  private final PdfGenerator generalPdfService;

  public PdfGenerator provider(Certificate certificate) {
    final var pdfSpecification = certificate.certificateModel().pdfSpecification();

    if (pdfSpecification == null || pdfSpecification instanceof GeneralPdfSpecification) {
      return generalPdfService;
    }

    return certificatePdfService;
  }

}
