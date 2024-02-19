package se.inera.intyg.certificateservice.domain.common.exception;

public class CertificateActionForbidden extends RuntimeException {

  public CertificateActionForbidden(String message) {
    super(message);
  }
}
