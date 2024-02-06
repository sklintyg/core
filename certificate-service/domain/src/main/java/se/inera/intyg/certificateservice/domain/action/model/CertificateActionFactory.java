package se.inera.intyg.certificateservice.domain.action.model;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

public class CertificateActionFactory {

  private CertificateActionFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateAction create(
      CertificateActionSpecification certificateActionSpecification) {
    return switch (certificateActionSpecification.certificateActionType()) {
      case CREATE -> new CertificateActionCreate(certificateActionSpecification);
      case READ -> new CertificateActionRead(certificateActionSpecification);
    };
  }
}
