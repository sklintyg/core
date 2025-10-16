package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateResponse.UpdateWithCertificateCandidateResponseBuilder;

@JsonDeserialize(builder = UpdateWithCertificateCandidateResponseBuilder.class)
@Value
@Builder
public class UpdateWithCertificateCandidateResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UpdateWithCertificateCandidateResponseBuilder {

  }
}
