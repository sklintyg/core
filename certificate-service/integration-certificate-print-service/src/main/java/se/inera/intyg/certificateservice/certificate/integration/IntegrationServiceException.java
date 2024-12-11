package se.inera.intyg.certificateservice.certificate.integration;


public class IntegrationServiceException extends RuntimeException {

  private final String applicationName;

  public IntegrationServiceException(String message, Throwable cause, String applicationName) {
    super(message, cause);
    this.applicationName = applicationName;
  }

  public String getApplicationName() {
    return applicationName;
  }
}
