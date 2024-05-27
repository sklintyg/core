package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse.GetCertificateFromMessageResponseBuilder;

@JsonDeserialize(builder = GetCertificateFromMessageResponseBuilder.class)
@Value
@Builder
public class GetCertificateFromMessageResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateFromMessageResponseBuilder {

  }
}
