package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;

@Value
@Builder
public class ElementConfigurationDiagnosis implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.DIAGNOSIS;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  @Getter(onMethod = @__(@Override))
  String description;
  FieldId id;
  @Builder.Default
  List<ElementDiagnosisTerminology> terminology = Collections.emptyList();
  @Builder.Default
  List<ElementDiagnosisListItem> list = Collections.emptyList();

  @Override
  public ElementValue emptyValue() {
    return ElementValueDiagnosisList.builder()
        .diagnoses(Collections.emptyList())
        .build();
  }
}
