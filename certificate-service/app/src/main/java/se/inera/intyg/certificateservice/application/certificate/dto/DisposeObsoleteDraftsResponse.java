package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsResponse.DisposeObsoleteDraftsResponseBuilder;

@JsonDeserialize(builder = DisposeObsoleteDraftsResponseBuilder.class)
@Value
@Builder
public class DisposeObsoleteDraftsResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DisposeObsoleteDraftsResponseBuilder {

  }
}
