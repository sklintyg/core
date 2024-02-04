package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse.CertificateExistsResponseBuilder;

@JsonDeserialize(builder = CertificateExistsResponseBuilder.class)
@Value
@Builder
public class CertificateExistsResponse {

  boolean exists;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateExistsResponseBuilder {

  }
}
