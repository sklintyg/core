package se.inera.intyg.certificateservice.domain.validation.model;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class ValidationError {

  ElementId elementId;
  ElementId categoryId;
  FieldId fieldId;
  ErrorMessage message;
}
