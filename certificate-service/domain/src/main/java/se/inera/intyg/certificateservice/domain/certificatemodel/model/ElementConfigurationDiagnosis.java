package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

@Value
@Builder
public class ElementConfigurationDiagnosis implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.MESSAGE;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  List<ElementDiagnosisTerminology> terminology;
  List<ElementDiagnosisListItem> list;

  @Override
  public ElementValue emptyValue() {
    return null;
  }
}
