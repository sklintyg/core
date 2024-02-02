package se.inera.intyg.certificateservice.domain.exception;

public class CertificateActionForbidden extends RuntimeException {

  public CertificateActionForbidden(String message) {
    super(message);
  }
}
