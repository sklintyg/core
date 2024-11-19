package se.inera.intyg.certificateservice.application.citizen.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePersonId;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

@Component
public class CitizenCertificateRequestValidator {

  public void validate(String certificateId) {
    validateCertificateId(certificateId);
  }

  public void validate(String certificateId, PersonIdDTO personId) {
    validateCertificateId(certificateId);
    validatePersonId(personId);
  }

  public void validate(PersonIdDTO personId) {
    validatePersonId(personId);
  }
}
