package se.inera.intyg.certificateservice.application.unit.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateList;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;

@Component
public class UnitStatisticsRequestValidator {

  public void validate(UnitStatisticsRequest request) {
    validateUser(request.getUser());
    validateUnitExtended(request.getUnit(), "Unit");
    validateUnit(request.getCareUnit(), "CareUnit");
    validateUnit(request.getCareProvider(), "CareProvider");
    validateList(request.getIssuedByUnitIds(), "IssuedByUnitIds");
  }
}
