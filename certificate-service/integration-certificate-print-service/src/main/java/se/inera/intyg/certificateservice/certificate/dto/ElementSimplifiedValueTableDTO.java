package se.inera.intyg.certificateservice.certificate.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ElementSimplifiedValueTableDTO implements ElementSimplifiedValueDTO {

  @With
  List<String> headings;
  List<List<String>> values;
  @Getter(onMethod = @__(@Override))
  ElementSimplifiedValueTypeDTO type = ElementSimplifiedValueTypeDTO.TABLE;
}
