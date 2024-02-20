package se.inera.intyg.certificateservice.domain.validation.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ValidationError {

  ElementId elementId;
  ElementId categoryId;
  FieldId fieldId;
  ErrorMessage message;
}
