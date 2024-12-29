package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class Correction {

  FieldId id;
  Double value;

  public String simplified() {
    if (value == null) {
      return "-";
    }

    return String.format("%.1f", value);
  }
}
