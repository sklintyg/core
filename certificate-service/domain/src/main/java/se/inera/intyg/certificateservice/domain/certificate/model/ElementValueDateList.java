package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueDateList implements ElementValue {

  FieldId dateListId;
  @With
  List<ElementValueDate> dateList;
}