package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateFromTemplateResponse.CreateCertificateFromTemplateResponseBuilder;

@JsonDeserialize(builder = CreateCertificateFromTemplateResponseBuilder.class)
@Value
@Builder
public class CreateCertificateFromTemplateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CreateCertificateFromTemplateResponseBuilder {

  }
}