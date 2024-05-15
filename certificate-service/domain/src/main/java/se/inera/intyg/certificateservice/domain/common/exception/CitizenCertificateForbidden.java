package se.inera.intyg.certificateservice.domain.common.exception;

import lombok.Getter;

@Getter
public class CitizenCertificateForbidden extends RuntimeException {

  public CitizenCertificateForbidden(String message) {
    super(message);
  }
}
