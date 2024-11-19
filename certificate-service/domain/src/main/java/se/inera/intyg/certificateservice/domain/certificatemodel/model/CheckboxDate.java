package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.Period;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class CheckboxDate {

  FieldId id;
  String label;
  Code code;
  Period min;
  Period max;
}
