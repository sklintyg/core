package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedElementValueOcularAcuity implements MappedElementValue {

  private String id;
  private double withCorrection;
  private double withoutCorrection;

}
