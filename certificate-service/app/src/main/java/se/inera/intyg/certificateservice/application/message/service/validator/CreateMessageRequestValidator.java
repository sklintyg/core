package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;

@Component
public class CreateMessageRequestValidator {

  public void validate(CreateMessageRequest createMessageRequest,
      String certificateId) {
    validateUser(createMessageRequest.getUser());
    validateUnitExtended(createMessageRequest.getUnit(), "Unit");
    validateUnit(createMessageRequest.getCareUnit(), "CareUnit");
    validateUnit(createMessageRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
    validatePatient(createMessageRequest.getPatient());
    if (createMessageRequest.getQuestionType() == null) {
      throw new IllegalArgumentException("Required parameter missing: questionType");
    }
  }
}
