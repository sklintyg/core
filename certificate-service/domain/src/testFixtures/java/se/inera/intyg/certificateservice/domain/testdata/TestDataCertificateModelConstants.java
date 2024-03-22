package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public class TestDataCertificateModelConstants {

  private TestDataCertificateModelConstants() {

  }

  public static final CertificateType FK7211_TYPE = new CertificateType("fk7211");
  public static final CertificateVersion FK7211_VERSION = new CertificateVersion("1.0");
  public static final Code FK7211_CODE_TYPE = new Code(
      "IGRAV",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om graviditet"
  );
  public static final String FK7211_PDF_PATH = "fk7211_v1.pdf";
}
