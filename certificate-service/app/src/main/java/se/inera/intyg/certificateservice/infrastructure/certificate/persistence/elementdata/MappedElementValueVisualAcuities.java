package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedElementValueVisualAcuities implements MappedElementValue {

  private MappedElementValueVisualAcuity rightEye;
  private MappedElementValueVisualAcuity leftEye;
  private MappedElementValueVisualAcuity binocular;

}