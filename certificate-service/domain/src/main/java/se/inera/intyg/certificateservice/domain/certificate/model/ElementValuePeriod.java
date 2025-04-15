package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValuePeriod implements ElementValue {

  ElementValueDate fromDate;
  ElementValueDate toDate;

  @Override
  public boolean isEmpty() {
    return fromDate == null && toDate == null;
  }
}
