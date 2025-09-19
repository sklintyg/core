package se.inera.intyg.certificateservice.certificate.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementSimplifiedValueLabeledTextDTO implements ElementSimplifiedValueDTO {

  String label;
  String text;

}
