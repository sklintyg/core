package se.inera.intyg.certificateservice.certificate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ElementSimplifiedValueTextDTO implements ElementSimplifiedValueDTO {

  @With
  String text;
  @Getter(onMethod = @__(@Override))
  ElementSimplifiedValueTypeDTO type = ElementSimplifiedValueTypeDTO.TEXT;
}
