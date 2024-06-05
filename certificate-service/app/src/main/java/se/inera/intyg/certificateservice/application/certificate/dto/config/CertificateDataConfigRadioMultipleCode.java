package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigRadioMultipleCode.CertificateDataConfigRadioMultipleCodeBuilder;

@JsonDeserialize(builder = CertificateDataConfigRadioMultipleCodeBuilder.class)
@Value
@Builder
public class CertificateDataConfigRadioMultipleCode implements CertificateDataConfig {

  @Getter(onMethod = @__(@Override))
  CertificateDataConfigType type = CertificateDataConfigType.UE_RADIO_MULTIPLE_CODE;
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
  Layout layout;
  List<RadioMultipleCode> list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataConfigRadioMultipleCodeBuilder {

  }
}
