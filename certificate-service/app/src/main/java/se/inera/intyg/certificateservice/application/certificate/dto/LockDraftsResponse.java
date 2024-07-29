package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsResponse.LockDraftsResponseBuilder;

@JsonDeserialize(builder = LockDraftsResponseBuilder.class)
@Value
@Builder
public class LockDraftsResponse {

  List<CertificateDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LockDraftsResponseBuilder {

  }
}
