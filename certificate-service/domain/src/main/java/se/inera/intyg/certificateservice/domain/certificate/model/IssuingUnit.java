package se.inera.intyg.certificateservice.domain.certificate.model;

public interface IssuingUnit {

  public HsaId getHsaId();

  public UnitAddress getAddress();

  public UnitName getName();

  public UnitContactInfo getContactInfo();

  public Inactive getInactive();
}
