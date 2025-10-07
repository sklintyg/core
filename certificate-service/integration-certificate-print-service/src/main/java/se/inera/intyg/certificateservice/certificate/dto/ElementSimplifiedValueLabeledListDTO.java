package se.inera.intyg.certificateservice.certificate.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@JsonTypeName("LABELED_LIST")
public class ElementSimplifiedValueLabeledListDTO implements ElementSimplifiedValueDTO {

  @With
  List<ElementSimplifiedValueLabeledTextDTO> list;

}
