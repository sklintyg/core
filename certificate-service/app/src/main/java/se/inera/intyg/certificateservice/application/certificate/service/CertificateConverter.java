package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class CertificateConverter {

  public CertificateDTO convert(Certificate certificate) {
    return null;
  }
}
