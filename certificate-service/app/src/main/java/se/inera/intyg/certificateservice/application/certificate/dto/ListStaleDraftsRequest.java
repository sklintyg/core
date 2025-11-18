package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsRequest.ListStaleDraftsRequestBuilder;

@JsonDeserialize(builder = ListStaleDraftsRequestBuilder.class)
@Value
@Builder
public class ListStaleDraftsRequest {

  LocalDateTime cutoffDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ListStaleDraftsRequestBuilder {

  }
}