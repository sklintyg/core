package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateMessageId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.DeleteMessageRequest;

@Component
public class DeleteMessageRequestValidator {

  public void validate(DeleteMessageRequest deleteMessageRequest,
      String messageId) {
    validateUser(deleteMessageRequest.getUser());
    validateUnitExtended(deleteMessageRequest.getUnit(), "Unit");
    validateUnit(deleteMessageRequest.getCareUnit(), "CareUnit");
    validateUnit(deleteMessageRequest.getCareProvider(), "CareProvider");
    validateMessageId(messageId);
  }
}
