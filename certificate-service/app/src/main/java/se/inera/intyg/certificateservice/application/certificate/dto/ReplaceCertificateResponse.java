package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateResponse.ReplaceCertificateResponseBuilder;

@JsonDeserialize(builder = ReplaceCertificateResponseBuilder.class)
@Value
@Builder
public class ReplaceCertificateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ReplaceCertificateResponseBuilder {

  }
}