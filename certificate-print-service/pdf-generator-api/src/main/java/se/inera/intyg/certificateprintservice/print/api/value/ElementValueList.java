package se.inera.intyg.certificateprintservice.print.api.value;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueList implements ElementValue {

  List<String> list;
}