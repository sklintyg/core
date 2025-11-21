package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsRequest.DisposeObsoleteDraftsRequestBuilder;

@JsonDeserialize(builder = DisposeObsoleteDraftsRequestBuilder.class)
@Value
@Builder
public class DisposeObsoleteDraftsRequest {

  String certificateId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DisposeObsoleteDraftsRequestBuilder {

  }
}