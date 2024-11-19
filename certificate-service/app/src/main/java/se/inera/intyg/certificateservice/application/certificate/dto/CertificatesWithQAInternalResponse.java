package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalResponse.CertificatesWithQAInternalResponseBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificatesWithQAInternalResponseBuilder.class)
public class CertificatesWithQAInternalResponse {

  String list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificatesWithQAInternalResponseBuilder {

  }
}
