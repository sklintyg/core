package se.inera.intyg.certificateprintservice.print.api.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText.ElementValueTextBuilder;

@Value
@Builder
@JsonDeserialize(builder = ElementValueTextBuilder.class)
public class ElementValueText implements ElementValue {

  String text;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ElementValueTextBuilder {

  }
}