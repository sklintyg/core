package se.inera.intyg.certificateservice.domain.certificate.model;

import static se.inera.intyg.certificateservice.domain.certificate.model.ElementValueType.DATE;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueDate implements ElementValue {

  LocalDate date;

  @Override
  public ElementValueType type() {
    return DATE;
  }
}
