package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ElementValueDateList implements ElementValue {
  
  @With
  List<ElementValueDate> dateList;
}
