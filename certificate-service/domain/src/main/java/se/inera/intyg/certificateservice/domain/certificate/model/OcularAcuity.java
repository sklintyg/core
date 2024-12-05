package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class OcularAcuity implements ElementValue {

  FieldId doubleId;
  Double withoutCorrection;
  Double withCorrection;

  @Override
  public boolean isEmpty() {
    return withoutCorrection == null && withCorrection == null;
  }
}
