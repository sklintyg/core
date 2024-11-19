package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidator;

@Value
@Builder
public class ElementValueCode implements ElementValue {

  FieldId codeId;
  @With
  String code;

  @Override
  public boolean isEmpty() {
    return !ElementValidator.isTextDefined(code);
  }
}
