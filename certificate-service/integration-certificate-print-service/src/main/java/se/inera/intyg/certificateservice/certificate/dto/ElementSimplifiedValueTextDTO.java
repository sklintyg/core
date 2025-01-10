package se.inera.intyg.certificateservice.certificate.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ElementSimplifiedValueTextDTO implements ElementSimplifiedValueDTO {

  @With
  String text;
}
