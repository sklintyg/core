package se.inera.intyg.certificateservice.domain.certificate.model;

public record Revision(long value) {

  public Revision increment() {
    return new Revision(value + 1);
  }
}
