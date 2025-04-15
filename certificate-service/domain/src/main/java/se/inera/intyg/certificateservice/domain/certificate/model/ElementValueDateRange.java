package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueDateRange implements ElementValue {

  FieldId id;
  @With
  LocalDate fromDate;
  @With
  LocalDate toDate;

  @Override
  public boolean isEmpty() {
    return fromDate == null && toDate == null;
  }
}
