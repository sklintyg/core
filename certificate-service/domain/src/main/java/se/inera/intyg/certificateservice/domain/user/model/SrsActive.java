package se.inera.intyg.certificateservice.domain.user.model;

public record SrsActive(Boolean value) {

  public SrsActive(Boolean value) {
    this.value = value != null && value;
  }
}