package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;

public class TestDataCertificateModelEntity {

  private TestDataCertificateModelEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateModelEntity CERTIFICATE_MODEL_ENTITY
      = new CertificateModelEntity(1, "fk7210", "1.0", "Intyg om graviditet");
}
