package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.Period;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementDate {

  String label;
  Period minDate;
  Period maxDate;

}
