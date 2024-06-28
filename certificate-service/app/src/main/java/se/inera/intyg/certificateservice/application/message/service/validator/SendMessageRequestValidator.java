package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateMessageId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;

@Component
public class SendMessageRequestValidator {

  public void validate(SendMessageRequest sendMessageRequest,
      String messageId) {
    validateUser(sendMessageRequest.getUser());
    validateUnitExtended(sendMessageRequest.getUnit(), "Unit");
    validateUnit(sendMessageRequest.getCareUnit(), "CareUnit");
    validateUnit(sendMessageRequest.getCareProvider(), "CareProvider");
    validatePatient(sendMessageRequest.getPatient());
    validateMessageId(messageId);
  }
}
