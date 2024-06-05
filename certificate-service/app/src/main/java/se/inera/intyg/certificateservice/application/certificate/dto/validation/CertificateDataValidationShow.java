package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationShow.CertificateDataValidationShowBuilder;

@JsonDeserialize(builder = CertificateDataValidationShowBuilder.class)
@Value
@Builder
public class CertificateDataValidationShow implements CertificateDataValidation {

  @Getter(onMethod = @__(@Override))
  CertificateDataValidationType type = CertificateDataValidationType.SHOW_VALIDATION;
  String questionId;
  String expression;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValidationShowBuilder {

  }
}
