package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsResponse.DeleteStaleDraftsResponseBuilder;

@JsonDeserialize(builder = DeleteStaleDraftsResponseBuilder.class)
@Value
@Builder
public class DeleteStaleDraftsResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DeleteStaleDraftsResponseBuilder {

  }
}
