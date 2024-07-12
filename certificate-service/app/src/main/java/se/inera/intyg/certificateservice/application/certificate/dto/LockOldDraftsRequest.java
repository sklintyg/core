package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsRequest.LockOldDraftsRequestBuilder;

@JsonDeserialize(builder = LockOldDraftsRequestBuilder.class)
@Value
@Builder
public class LockOldDraftsRequest {

  LocalDateTime cutoffDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LockOldDraftsRequestBuilder {

  }
}
