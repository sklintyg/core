package se.inera.intyg.certificateservice.application.unit.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;

@Component
public class GetUnitMessagesRequestValidator {

  public void validate(GetUnitMessagesRequest request) {
    validateUser(request.getUser());
    validateUnitExtended(request.getUnit(), "Unit");
    validateUnit(request.getCareUnit(), "CareUnit");
    validateUnit(request.getCareProvider(), "CareProvider");
    if (request.getMessagesQueryCriteria() == null) {
      throw new IllegalArgumentException("Required parameter missing: MessagesQueryCriteria");
    }
  }
}
