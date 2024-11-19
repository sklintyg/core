package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignResponse.CertificateReadyForSignResponseBuilder;

@JsonDeserialize(builder = CertificateReadyForSignResponseBuilder.class)
@Value
@Builder
public class CertificateReadyForSignResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateReadyForSignResponseBuilder {

  }
}
