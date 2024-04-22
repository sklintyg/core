package se.inera.intyg.certificateservice.domain.common.exception;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class CertificateActionForbidden extends RuntimeException {

  private final List<String> reason;

  public CertificateActionForbidden(String message) {
    super(message);
    this.reason = Collections.emptyList();
  }

  public CertificateActionForbidden(String message, List<String> reason) {
    super(message);
    this.reason = reason;
  }
}
