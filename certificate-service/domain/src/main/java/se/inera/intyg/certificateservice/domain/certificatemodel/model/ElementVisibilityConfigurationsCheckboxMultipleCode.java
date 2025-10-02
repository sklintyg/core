package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementVisibilityConfigurationsCheckboxMultipleCode implements
    ElementVisibilityConfiguration {

  ElementType type = ElementType.CHECKBOX_MULTIPLE_CODE;
  ElementId elementId;
  FieldId fieldId;
}