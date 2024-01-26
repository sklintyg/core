package se.inera.intyg.certificateservice.model;

public class CertificateActionFactory {

  private CertificateActionFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateAction create(
      CertificateActionSpecification certificateActionSpecification) {
    return switch (certificateActionSpecification.getCertificateActionType()) {
      case CREATE -> new CertificateActionCreate(certificateActionSpecification);
    };
  }
}
