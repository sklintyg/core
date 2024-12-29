package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VisualAcuity implements ElementValue {

  Correction withoutCorrection;
  Correction withCorrection;

  @Override
  public boolean isEmpty() {
    return withoutCorrection.value() == null && withCorrection.value() == null;
  }

  public List<String> simplified() {
    return List.of(
        withoutCorrection == null
            ? "-"
            : withoutCorrection.simplified(),
        withCorrection == null
            ? "-"
            : withCorrection.simplified()
    );
  }
}