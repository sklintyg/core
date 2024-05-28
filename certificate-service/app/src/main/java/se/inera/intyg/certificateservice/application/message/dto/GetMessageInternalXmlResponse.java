package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse.GetMessageInternalXmlResponseBuilder;

@JsonDeserialize(builder = GetMessageInternalXmlResponseBuilder.class)
@Value
@Builder
public class GetMessageInternalXmlResponse {

  String xml;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetMessageInternalXmlResponseBuilder {

  }
}
