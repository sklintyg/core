package se.inera.intyg.certificateservice.domain.message.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.message.model.UnansweredQAs.UnansweredQAsBuilder;

@JsonDeserialize(builder = UnansweredQAsBuilder.class)
@Value
@Builder
public class UnansweredQAs {

  @JsonProperty("complement")
  int complement;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnansweredQAsBuilder {

  }
}
