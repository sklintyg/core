package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsResponse.LockOldDraftsResponseBuilder;

@JsonDeserialize(builder = LockOldDraftsResponseBuilder.class)
@Value
@Builder
public class LockOldDraftsResponse {

  List<CertificateDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LockOldDraftsResponseBuilder {

  }
}
