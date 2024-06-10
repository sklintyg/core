package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedElementValueBoolean implements MappedElementValue {

  String booleanId;
  Boolean value;
}
