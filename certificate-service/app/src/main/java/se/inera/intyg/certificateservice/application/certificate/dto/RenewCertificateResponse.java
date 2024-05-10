package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse.RenewCertificateResponseBuilder;

@JsonDeserialize(builder = RenewCertificateResponseBuilder.class)
@Value
@Builder
public class RenewCertificateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RenewCertificateResponseBuilder {

  }
}