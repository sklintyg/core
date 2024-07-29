package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsRequest.LockDraftsRequestBuilder;

@JsonDeserialize(builder = LockDraftsRequestBuilder.class)
@Value
@Builder
public class LockDraftsRequest {

  LocalDateTime cutoffDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LockDraftsRequestBuilder {

  }
}
