package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationDisable.CertificateDataValidationDisableBuilder;

@JsonDeserialize(builder = CertificateDataValidationDisableBuilder.class)
@Value
@Builder
public class CertificateDataValidationDisable implements CertificateDataValidation {

  @Getter(onMethod = @__(@Override))
  CertificateDataValidationType type = CertificateDataValidationType.DISABLE_VALIDATION;
  String questionId;
  String expression;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValidationDisableBuilder {

  }
}
