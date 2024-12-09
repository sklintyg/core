package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OcularAcuity implements ElementValue {

  Correction withoutCorrection;
  Correction withCorrection;

  @Override
  public boolean isEmpty() {
    return withoutCorrection.value() == null && withCorrection.value() == null;
  }
}