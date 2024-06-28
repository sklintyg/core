package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigTextField.CertificateDataConfigTextFieldBuilder;

@JsonDeserialize(builder = CertificateDataConfigTextFieldBuilder.class)
@Value
@Builder
public class CertificateDataConfigTextField implements CertificateDataConfig {

  @Getter(onMethod = @__(@Override))
  CertificateDataConfigType type = CertificateDataConfigType.UE_TEXTFIELD;
  @Getter(onMethod = @__(@Override))
  String header;
  @Getter(onMethod = @__(@Override))
  String label;
  @Getter(onMethod = @__(@Override))
  String icon;
  @Getter(onMethod = @__(@Override))
  String text;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  Accordion accordion;
  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataConfigTextFieldBuilder {

  }
}
