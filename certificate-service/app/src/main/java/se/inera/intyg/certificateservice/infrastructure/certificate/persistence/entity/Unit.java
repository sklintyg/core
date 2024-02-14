package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Unit {

  private String hsaId;
  private String name;
  private UnitType type;
}
