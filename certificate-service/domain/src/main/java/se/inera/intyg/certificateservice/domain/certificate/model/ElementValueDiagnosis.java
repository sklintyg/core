package se.inera.intyg.certificateservice.domain.certificate.model;


import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidator;


@Value
@Builder
public class ElementValueDiagnosis implements ElementValue {

  FieldId id;
  String terminology;
  String code;
  String description;

  @Override
  public boolean isEmpty() {
    return !ElementValidator.isTextDefined(code) || !ElementValidator.isTextDefined(description);
  }
}
