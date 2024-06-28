package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateMessageId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerRequest;

@Component
public class SaveAnswerRequestValidator {

  public void validate(SaveAnswerRequest saveAnswerRequest,
      String messageId) {
    validateUser(saveAnswerRequest.getUser());
    validateUnitExtended(saveAnswerRequest.getUnit(), "Unit");
    validateUnit(saveAnswerRequest.getCareUnit(), "CareUnit");
    validateUnit(saveAnswerRequest.getCareProvider(), "CareProvider");
    validateMessageId(messageId);
  }
}
