package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@Value
@Builder
public class CareProvider {

  HsaId hsaId;
  UnitName name;
}
