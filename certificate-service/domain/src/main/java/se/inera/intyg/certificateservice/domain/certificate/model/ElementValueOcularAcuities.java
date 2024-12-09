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
    return rightEye.withoutCorrection().value() == null
        && rightEye.withCorrection().value() == null
        && leftEye.withoutCorrection().value() == null
        && leftEye.withCorrection().value() == null
        && binocular.withoutCorrection().value() == null
        && binocular.withCorrection().value() == null;
  }
}