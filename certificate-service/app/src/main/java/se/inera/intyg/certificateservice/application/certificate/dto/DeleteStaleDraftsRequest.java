package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsRequest.DeleteStaleDraftsRequestBuilder;

@JsonDeserialize(builder = DeleteStaleDraftsRequestBuilder.class)
@Value
@Builder
public class DeleteStaleDraftsRequest {

  String certificateId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DeleteStaleDraftsRequestBuilder {

  }
}