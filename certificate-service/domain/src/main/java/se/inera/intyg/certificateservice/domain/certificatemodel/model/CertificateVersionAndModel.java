package se.inera.intyg.certificateservice.domain.certificatemodel.model;

public record CertificateVersionAndModel(String version, CertificateModel model) {

  public boolean isActive() {
    return model != null && model.isActive();
  }
}