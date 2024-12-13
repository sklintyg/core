package se.inera.intyg.certificateprintservice.print.api.value;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueText implements ElementValue {

  String text;

}