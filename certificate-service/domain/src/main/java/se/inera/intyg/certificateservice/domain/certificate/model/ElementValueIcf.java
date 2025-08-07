package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidator;

@Value
@Builder
public class ElementValueIcf implements ElementValue {

  FieldId icfId;
  String modalLabel;
  String collectionsLabel;
  String placeholder;

  @Override
  public boolean isEmpty() {
    return !ElementValidator.isTextDefined(modalLabel) ||
        !ElementValidator.isTextDefined(collectionsLabel) ||
        !ElementValidator.isTextDefined(placeholder);
  }
}
