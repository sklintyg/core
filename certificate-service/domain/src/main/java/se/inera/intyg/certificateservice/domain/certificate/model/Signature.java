package se.inera.intyg.certificateservice.domain.certificate.model;

public record Signature(String value) {

  public boolean isEmpty() {
    return value == null || value.isBlank();
  }
}
