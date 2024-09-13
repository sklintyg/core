package se.inera.intyg.certificateservice.application.patient.service.validator;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQARequest;

@Component
public class CertificatesWithQARequestValidator {

  public void validate(CertificatesInternalWithQARequest request) {
    if (request.getCertificateIds() == null) {
      throw new IllegalArgumentException("Required parameter missing: certificateIds");
    }
  }
}
