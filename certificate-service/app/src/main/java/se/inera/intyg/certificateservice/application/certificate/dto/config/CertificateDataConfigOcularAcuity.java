package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigOcularAcuity.CertificateDataConfigOcularAcuityBuilder;

@JsonDeserialize(builder = CertificateDataConfigOcularAcuityBuilder.class)
@Value
@Builder
public class CertificateDataConfigOcularAcuity implements CertificateDataConfig {

  @Getter(onMethod = @__(@Override))
  CertificateDataConfigType type = CertificateDataConfigType.UE_OCULAR_ACUITY;
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
  @Getter(onMethod = @__(@Override))
  Message message;
  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataConfigOcularAcuityBuilder {

  }
}
