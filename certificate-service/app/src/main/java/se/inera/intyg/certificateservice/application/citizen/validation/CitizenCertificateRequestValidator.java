package se.inera.intyg.certificateservice.application.citizen.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCitizenId;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

@Component
public class CitizenCertificateRequestValidator {

  public void validate(String certificateId) {
    validateCertificateId(certificateId);
  }

  public void validate(String certificateId, PersonIdDTO personId) {
    validateCertificateId(certificateId);
    validateCitizenId(personId);
  }

  public void validate(PersonIdDTO personId) {
    validateCitizenId(personId);
  }
}
