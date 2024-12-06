package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OcularAcuity implements ElementValue {

  WithoutCorrection withoutCorrection;
  WithCorrection withCorrection;

  @Override
  public boolean isEmpty() {
    return withoutCorrection == null && withCorrection == null;
  }
}