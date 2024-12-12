package se.inera.intyg.certificateprintservice.print.api.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueList.ElementValueListBuilder;

@Value
@Builder
@JsonDeserialize(builder = ElementValueListBuilder.class)
public class ElementValueList implements ElementValue {

  List<String> list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ElementValueListBuilder {

  }
}