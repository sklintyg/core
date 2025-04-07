package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ElementValueDiagnosisList implements ElementValue {

  @Builder.Default
  @With
  List<ElementValueDiagnosis> diagnoses = Collections.emptyList();

  @Override
  public boolean isEmpty() {
    if (diagnoses == null || diagnoses.isEmpty()) {
      return true;
    }

    return diagnoses.stream().allMatch(ElementValueDiagnosis::isEmpty);
  }
}
