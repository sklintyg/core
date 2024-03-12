package se.inera.intyg.certificateservice.domain.unit.model;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;

public interface IssuingUnit {

  HsaId hsaId();

  UnitAddress address();

  UnitName name();

  UnitContactInfo contactInfo();

  WorkplaceCode workplaceCode();

  Inactive inactive();
}
