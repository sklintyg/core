package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList.CertificateDataConfigCheckboxDateRangeListBuilder;

@JsonDeserialize(builder = CertificateDataConfigCheckboxDateRangeListBuilder.class)
@Value
@Builder
public class CertificateDataConfigCheckboxDateRangeList implements CertificateDataConfig {

  @Getter(onMethod = @__(@Override))
  CertificateDataConfigType type = CertificateDataConfigType.UE_CHECKBOX_DATE_RANGE_LIST;
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
  String previousDateRangeText;
  boolean hideWorkingHours;
  List<CheckboxDateRangeConfig> list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataConfigCheckboxDateRangeListBuilder {

  }
}
