package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class DateRange {

  FieldId dateRangeId;
  LocalDate to;
  LocalDate from;

}
