package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse.CreateCertificateResponseBuilder;

@JsonDeserialize(builder = CreateCertificateResponseBuilder.class)
@Value
@Builder
public class CreateCertificateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CreateCertificateResponseBuilder {

  }
}
