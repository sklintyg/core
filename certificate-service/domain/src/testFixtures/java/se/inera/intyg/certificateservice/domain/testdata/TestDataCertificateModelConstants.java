package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public class TestDataCertificateModelConstants {

  private TestDataCertificateModelConstants() {

  }

  public static final CertificateType FK7211_TYPE = new CertificateType("fk7211");
  public static final CertificateType FK7443_TYPE = new CertificateType("fk7443");
  public static final CertificateVersion FK7211_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7443_VERSION = new CertificateVersion("1.0");
  public static final Code FK7211_CODE_TYPE = new Code(
      "IGRAV",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om graviditet"
  );
  public static final Code FK7443_CODE_TYPE = new Code(
      "ITFP",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om tillfällig föräldrapenning"
  );
  public static final SchematronPath FK7211_SCHEMATRON_PATH = new SchematronPath(
      "fk7211/schematron/igrav.v1.sch");
  public static final SchematronPath FK7443_SCHEMATRON_PATH = new SchematronPath(
      "fk7443/schematron/itfp.v1.sch");
  public static final String FK7211_PDF_PATH = "fk7211/pdf/fk7211_v1.pdf";
  public static final String FK7443_PDF_PATH = "fk7443/pdf/fk7443_v1.pdf";
  public static final String FK7211_NAME = "Intyg om graviditet";
  public static final String FK7443_NAME = "Intyg om tillfällig föräldrapenning";
}
