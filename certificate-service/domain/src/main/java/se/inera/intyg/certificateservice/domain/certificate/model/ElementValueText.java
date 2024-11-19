package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidator;

@Value
@Builder
public class ElementValueText implements ElementValue {

  FieldId textId;
  @With
  String text;

  @Override
  public boolean isEmpty() {
    return !ElementValidator.isTextDefined(text);
  }
}
