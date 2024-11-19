package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueDate implements ElementValue {

  FieldId dateId;
  @With
  LocalDate date;

  @Override
  public boolean isEmpty() {
    return date == null;
  }
}
