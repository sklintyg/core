package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationText.CertificateDataValidationTextBuilder;

@JsonDeserialize(builder = CertificateDataValidationTextBuilder.class)
@Value
@Builder
public class CertificateDataValidationText implements CertificateDataValidation {

  @Getter(onMethod = @__(@Override))
  CertificateDataValidationType type = CertificateDataValidationType.TEXT_VALIDATION;
  String id;
  short limit;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValidationTextBuilder {

  }
}
