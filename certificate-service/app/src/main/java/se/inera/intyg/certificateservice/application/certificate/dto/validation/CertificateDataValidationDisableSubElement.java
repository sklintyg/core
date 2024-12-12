package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationDisableSubElement.CertificateDataValidationDisableSubElementBuilder;

@JsonDeserialize(builder = CertificateDataValidationDisableSubElementBuilder.class)
@Value
@Builder
public class CertificateDataValidationDisableSubElement implements CertificateDataValidation {

  @Getter(onMethod = @__(@Override))
  CertificateDataValidationType type = CertificateDataValidationType.DISABLE_SUB_ELEMENT_VALIDATION;
  String questionId;
  String expression;
  List<String> id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValidationDisableSubElementBuilder {

  }
}
