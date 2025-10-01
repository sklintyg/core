package se.inera.intyg.certificateservice.domain.certificate.model;

public interface ElementValue {

  boolean isEmpty();

  default String type() {
    return this.getClass().getTypeName();
  }
}
