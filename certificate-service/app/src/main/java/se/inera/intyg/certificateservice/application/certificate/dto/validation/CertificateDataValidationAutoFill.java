package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationAutoFill.CertificateDataValidationAutoFillBuilder;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;

@JsonDeserialize(builder = CertificateDataValidationAutoFillBuilder.class)
@Value
@Builder
public class CertificateDataValidationAutoFill implements CertificateDataValidation {

  @Getter(onMethod = @__(@Override))
  CertificateDataValidationType type = CertificateDataValidationType.AUTO_FILL_VALIDATION;
  String questionId;
  String expression;
  CertificateDataValue fillValue;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValidationAutoFillBuilder {

  }
}
