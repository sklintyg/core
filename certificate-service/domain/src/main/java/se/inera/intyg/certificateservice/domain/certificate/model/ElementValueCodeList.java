package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueCodeList implements ElementValue {

  FieldId id;
  @With
  List<ElementValueCode> list;

  @Override
  public boolean isEmpty() {
    if (list == null || list.isEmpty()) {
      return true;
    }

    return list.stream().allMatch(ElementValueCode::isEmpty);
  }
}
