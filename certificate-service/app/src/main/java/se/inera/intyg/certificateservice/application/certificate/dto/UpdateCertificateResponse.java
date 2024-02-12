package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse.UpdateCertificateResponseBuilder;

@JsonDeserialize(builder = UpdateCertificateResponseBuilder.class)
@Value
@Builder
public class UpdateCertificateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UpdateCertificateResponseBuilder {

  }
}
