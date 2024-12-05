package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueOcularAcuities implements ElementValue {

  OcularAcuity rightEye;
  OcularAcuity leftEye;
  OcularAcuity binocular;

  @Override
  public boolean isEmpty() {
    return rightEye == null && leftEye == null && binocular == null;
  }
}
