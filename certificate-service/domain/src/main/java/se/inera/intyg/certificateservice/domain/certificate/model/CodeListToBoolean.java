package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CodeListToBoolean implements ElementValue {

  @Override
  public boolean isEmpty() {
    return true;
  }
}