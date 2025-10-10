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

  public static CheckboxDate create(Code code, Period min, Period max) {
    return CheckboxDate.builder()
        .id(new FieldId(code.code()))
        .label(code.displayName())
        .code(code)
        .max(max)
        .min(min)
        .build();
  }
}
