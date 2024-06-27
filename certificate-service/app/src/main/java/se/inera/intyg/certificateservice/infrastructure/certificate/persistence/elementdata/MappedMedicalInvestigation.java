package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedMedicalInvestigation {

  private String id;
  private MappedDate date;
  private MappedText informationSource;
  private MappedCode investigationType;

}
