package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationHide.CertificateDataValidationHideBuilder;

@JsonDeserialize(builder = CertificateDataValidationHideBuilder.class)
@Value
@Builder
public class CertificateDataValidationHide implements CertificateDataValidation {

  @Getter(onMethod = @__(@Override))
  CertificateDataValidationType type = CertificateDataValidationType.HIDE_VALIDATION;
  String questionId;
  String expression;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValidationHideBuilder {

  }
}
