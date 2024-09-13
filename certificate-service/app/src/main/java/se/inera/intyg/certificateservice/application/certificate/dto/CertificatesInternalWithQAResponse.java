package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQAResponse.CertificatesInternalWithQAResponseBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificatesInternalWithQAResponseBuilder.class)
public class CertificatesInternalWithQAResponse {

  String list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificatesInternalWithQAResponseBuilder {

  }
}
