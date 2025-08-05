package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueInteger implements ElementValue {

  FieldId integerId;
  @With
  Integer value;
  String unitOfMeasurement;

  @Override
  public boolean isEmpty() {
    return value == null;
  }
}
