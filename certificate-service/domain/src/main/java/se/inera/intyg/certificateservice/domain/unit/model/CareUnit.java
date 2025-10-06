package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@Value
@Builder
@EqualsAndHashCode(exclude = "inactive")
public class CareUnit implements IssuingUnit {

  HsaId hsaId;
  UnitAddress address;
  UnitName name;
  UnitContactInfo contactInfo;
  WorkplaceCode workplaceCode;
  Inactive inactive;
}
