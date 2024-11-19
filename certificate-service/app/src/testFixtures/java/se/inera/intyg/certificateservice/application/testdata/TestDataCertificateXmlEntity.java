package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateXmlEntity;

public class TestDataCertificateXmlEntity {

  private TestDataCertificateXmlEntity() {
    throw new IllegalStateException("Utility class");
  }

  private static final String XML = "<xml/>";
  public static final CertificateXmlEntity CERTIFICATE_XML_ENTITY = new CertificateXmlEntity(XML);

}
