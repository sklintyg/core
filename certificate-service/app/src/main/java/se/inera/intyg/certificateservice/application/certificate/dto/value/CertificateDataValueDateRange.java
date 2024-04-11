package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange.CertificateDataValueDateRangeBuilder;

@JsonDeserialize(builder = CertificateDataValueDateRangeBuilder.class)
@Value
@Builder
public class CertificateDataValueDateRange implements CertificateDataValue {

  @Getter(onMethod = @__(@Override))
  CertificateDataValueType type = CertificateDataValueType.DATE_RANGE;
  String id;
  LocalDate to;
  LocalDate from;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValueDateRangeBuilder {

  }
}
