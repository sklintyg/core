package se.inera.intyg.certificateservice.certificate.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@JsonTypeName("TABLE")
public class ElementSimplifiedValueTableDTO implements ElementSimplifiedValueDTO {

  @With
  List<String> headings;
  List<List<String>> values;
}
