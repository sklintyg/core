package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;

public class TestDataCertificate {

  private TestDataCertificate() {

  }

  public static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  public static final Revision REVISION = new Revision(0L);

}
