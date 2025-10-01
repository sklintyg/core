package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement.CertificateDataElementBuilder;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.ValidationError;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;


@JsonDeserialize(builder = CertificateDataElementBuilder.class)
@Value
@Builder
public class CertificateDataElement {

  String id;
  String parent;
  int index;
  CertificateDataConfig config;
  @With
  CertificateDataValue value;
  CertificateDataValidation[] validation;
  ValidationError[] validationError;
  CertificateDataElementStyleEnum style;
  Boolean visible;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataElementBuilder {

  }
}