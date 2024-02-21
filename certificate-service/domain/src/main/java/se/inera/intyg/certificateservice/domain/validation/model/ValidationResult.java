package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidationResult {

  List<ValidationError> errors;
}
