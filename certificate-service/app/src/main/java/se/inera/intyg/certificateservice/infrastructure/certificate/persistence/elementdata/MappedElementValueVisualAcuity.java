package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedElementValueVisualAcuity implements MappedElementValue {

  private MappedElementValueDouble withCorrection;
  private MappedElementValueDouble withoutCorrection;

}