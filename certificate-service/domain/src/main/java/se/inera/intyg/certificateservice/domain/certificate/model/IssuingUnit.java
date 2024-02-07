package se.inera.intyg.certificateservice.domain.certificate.model;

public interface IssuingUnit {

  public HsaId hsaId();

  public UnitAddress address();

  public UnitName name();

  public UnitContactInfo contactInfo();

  public Inactive inactive();
}
