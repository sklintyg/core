package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateMessage;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;

@Component
public class AnswerComplementRequestValidator {

  public void validate(AnswerComplementRequest answerComplementRequest, String certificateId) {
    validateUser(answerComplementRequest.getUser());
    validateUnitExtended(answerComplementRequest.getUnit(), "Unit");
    validateUnit(answerComplementRequest.getCareUnit(), "CareUnit");
    validateUnit(answerComplementRequest.getCareProvider(), "CareProvider");
    validateMessage(answerComplementRequest.getMessage());
    validateCertificateId(certificateId);
  }
}
