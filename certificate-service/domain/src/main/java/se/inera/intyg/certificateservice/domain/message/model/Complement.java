package se.inera.intyg.certificateservice.domain.message.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class Complement {

  ElementId elementId;
  FieldId fieldId;
  Content content;
}
