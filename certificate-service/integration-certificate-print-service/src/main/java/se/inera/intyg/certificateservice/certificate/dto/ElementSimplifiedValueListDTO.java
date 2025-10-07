package se.inera.intyg.certificateservice.certificate.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@JsonTypeName("LIST")
public class ElementSimplifiedValueListDTO implements ElementSimplifiedValueDTO {

  @With
  List<String> list;
}
