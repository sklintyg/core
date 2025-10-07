package se.inera.intyg.certificateservice.certificate.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@JsonTypeName("TEXT")
public class ElementSimplifiedValueTextDTO implements ElementSimplifiedValueDTO {

  @With
  String text;
}
