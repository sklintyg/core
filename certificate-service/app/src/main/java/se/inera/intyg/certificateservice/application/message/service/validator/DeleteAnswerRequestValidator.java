package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateMessageId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerRequest;

@Component
public class DeleteAnswerRequestValidator {

  public void validate(DeleteAnswerRequest deleteAnswerRequest,
      String messageId) {
    validateUser(deleteAnswerRequest.getUser());
    validateUnitExtended(deleteAnswerRequest.getUnit(), "Unit");
    validateUnit(deleteAnswerRequest.getCareUnit(), "CareUnit");
    validateUnit(deleteAnswerRequest.getCareProvider(), "CareProvider");
    validateMessageId(messageId);
  }
}
