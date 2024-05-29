package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse.GetCertificateMetadataResponseBuilder;

@JsonDeserialize(builder = GetCertificateMetadataResponseBuilder.class)
@Value
@Builder
public class GetCertificateInternalMetadataResponse {

  CertificateMetadataDTO certificateMetadata;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateMetadataResponseBuilder {

  }
}
