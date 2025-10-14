package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;

public interface PdfGenerator {

  Pdf generate(Certificate certificate, PdfGeneratorOptions options);

}
