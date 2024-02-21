package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidationResult {

  @Builder.Default
  List<ValidationError> errors = Collections.emptyList();

  public boolean isValid() {
    return errors.isEmpty();
  }


  public boolean isInvalid() {
    return !errors.isEmpty();
  }
}
