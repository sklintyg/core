package se.inera.intyg.certificateservice.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonIdDTO {

  private PersonIdTypeDTO type;
  private String id;

}
