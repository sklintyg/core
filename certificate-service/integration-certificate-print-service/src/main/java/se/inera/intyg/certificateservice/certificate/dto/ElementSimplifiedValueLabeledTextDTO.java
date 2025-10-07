package se.inera.intyg.certificateservice.certificate.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonTypeName("LABELED_TEXT")
public class ElementSimplifiedValueLabeledTextDTO implements ElementSimplifiedValueDTO {

  String label;
  String text;

}
