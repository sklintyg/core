package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

public class PrefillException extends Exception {

  public PrefillException(String message, Exception e) {
    super(message, e);
  }

  public PrefillException(String message) {
    super(message);
  }
}
