package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateResponse.GetCertificateCandidateResponseBuilder;

@JsonDeserialize(builder = GetCertificateCandidateResponseBuilder.class)
@Value
@Builder
public class GetCertificateCandidateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateCandidateResponseBuilder {

  }
}
