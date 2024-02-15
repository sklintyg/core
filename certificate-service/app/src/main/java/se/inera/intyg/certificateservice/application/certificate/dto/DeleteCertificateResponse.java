package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse.DeleteCertificateResponseBuilder;

@JsonDeserialize(builder = DeleteCertificateResponseBuilder.class)
@Value
@Builder
public class DeleteCertificateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DeleteCertificateResponseBuilder {

  }
}