package se.inera.intyg.certificateservice.application.patient.service.validator;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest;

@Component
public class CertificatesWithQARequestValidator {

  public void validate(CertificatesWithQAInternalRequest request) {
    if (request.getCertificateIds() == null) {
      throw new IllegalArgumentException("Required parameter missing: certificateIds");
    }
  }
}
