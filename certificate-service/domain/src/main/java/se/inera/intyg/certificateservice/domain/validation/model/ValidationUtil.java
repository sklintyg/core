package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class ValidationUtil {

  private ValidationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static ValidationError errorMessage(
      ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId,
      String message) {
    return ValidationError.builder()
        .elementId(data.id())
        .fieldId(fieldId)
        .categoryId(categoryId.orElse(null))
        .message(new ErrorMessage(message))
        .build();
  }
}
