package se.inera.intyg.certificateservice.certificate.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ElementSimplifiedValueLabeledListDTO implements ElementSimplifiedValueDTO {

  @With
  List<ElementSimplifiedValueLabeledTextDTO> list;

}
