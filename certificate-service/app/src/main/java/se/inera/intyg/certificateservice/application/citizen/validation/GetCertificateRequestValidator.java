package se.inera.intyg.certificateservice.application.citizen.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;

import org.springframework.stereotype.Component;

@Component
public class GetCertificateRequestValidator {

  public void validate(String certificateId) {
    validateCertificateId(certificateId);
  }
}
