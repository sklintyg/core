package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueBoolean implements ElementValue {

  FieldId booleanId;
  @With
  Boolean value;

  @Override
  public boolean isEmpty() {
    return value == null;
  }

}
