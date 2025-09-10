package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificateInternalResponse.GetSickLeaveCertificateInternalResponseBuilder;

@JsonDeserialize(builder = GetSickLeaveCertificateInternalResponseBuilder.class)
@Value
@Builder
public class GetSickLeaveCertificateInternalResponse {

  boolean isAvailable;
  SjukfallCertificate certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetSickLeaveCertificateInternalResponseBuilder {

  }

}
