package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DatePeriodDate.DatePeriodDateBuilder;

@JsonDeserialize(builder = DatePeriodDateBuilder.class)
@Value
@Builder
public class DatePeriodDate {

  String id;
  String label;
  LocalDate maxDate;
  LocalDate minDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DatePeriodDateBuilder {

  }
}
