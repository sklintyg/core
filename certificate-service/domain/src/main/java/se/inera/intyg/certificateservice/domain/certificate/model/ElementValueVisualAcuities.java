package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueVisualAcuities implements ElementValue {

  VisualAcuity rightEye;
  VisualAcuity leftEye;
  VisualAcuity binocular;

  @Override
  public boolean isEmpty() {
    return rightEye.isEmpty() && leftEye.isEmpty() && binocular.isEmpty();
  }
}
