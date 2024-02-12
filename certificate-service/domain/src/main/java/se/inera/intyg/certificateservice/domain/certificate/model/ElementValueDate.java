package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueDate implements ElementValue {

  LocalDate date;
  
}
