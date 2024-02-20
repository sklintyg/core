package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse.ValidateCertificateResponseBuilder;

@JsonDeserialize(builder = ValidateCertificateResponseBuilder.class)
@Value
@Builder
public class ValidateCertificateResponse {

  ValidationErrorDTO[] validationErrors;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ValidateCertificateResponseBuilder {

  }
}
