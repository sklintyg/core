package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateMessageId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateQuestion;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;

@Component
public class SaveMessageRequestValidator {

  public void validate(SaveMessageRequest saveMessageRequest,
      String messageId) {
    validateUser(saveMessageRequest.getUser());
    validateUnitExtended(saveMessageRequest.getUnit(), "Unit");
    validateUnit(saveMessageRequest.getCareUnit(), "CareUnit");
    validateUnit(saveMessageRequest.getCareProvider(), "CareProvider");
    validateMessageId(messageId);
    validateQuestion(saveMessageRequest.getQuestion());
  }
}
