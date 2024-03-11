package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse.SignCertificateResponseBuilder;

@JsonDeserialize(builder = SignCertificateResponseBuilder.class)
@Value
@Builder
public class SignCertificateResponse {

  @JsonPOJOBuilder(withPrefix = "")
  public static class SignCertificateResponseBuilder {

  }
}
