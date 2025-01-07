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

  public List<String> simplified(String label) {
    return List.of(
        label,
        withoutCorrection == null
            ? "-"
            : withoutCorrection.simplified(),
        withCorrection == null
            ? "-"
            : withCorrection.simplified()
    );
  }
}