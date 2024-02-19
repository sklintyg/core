package se.inera.intyg.certificateservice.domain.unit.model;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;

public interface IssuingUnit {

  public HsaId hsaId();

  public UnitAddress address();

  public UnitName name();

  public UnitContactInfo contactInfo();

  public Inactive inactive();
}
