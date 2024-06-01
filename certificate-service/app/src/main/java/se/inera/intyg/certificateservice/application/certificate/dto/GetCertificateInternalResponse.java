package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse.GetCertificateInternalResponseBuilder;

@JsonDeserialize(builder = GetCertificateInternalResponseBuilder.class)
@Value
@Builder
public class GetCertificateInternalResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateInternalResponseBuilder {

  }
}
