package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidationError {

  String id;
  String category;
  String field;
  String type;
  String text;
}
