package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

public class TestDataCertificateModelConstants {

  private TestDataCertificateModelConstants() {

  }

  public static final CertificateType FK7210_TYPE = new CertificateType("fk7210");
  public static final CertificateType FK7472_TYPE = new CertificateType("fk7472");
  public static final CertificateVersion FK7210_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7472_VERSION = new CertificateVersion("1.0");
  public static final CertificateModelId FK7210_ID = CertificateModelId.builder()
      .type(FK7210_TYPE)
      .version(FK7210_VERSION)
      .build();
  public static final Code FK7210_CODE_TYPE = new Code(
      "IGRAV",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om graviditet"
  );
  public static final Code FK7472_CODE_TYPE = new Code(
      "ITFP",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om tillfällig föräldrapenning"
  );
  public static final SchematronPath FK7210_SCHEMATRON_PATH = new SchematronPath(
      "fk7210/schematron/igrav.v1.sch");
  public static final SchematronPath FK7472_SCHEMATRON_PATH = new SchematronPath(
      "fk7472/schematron/itfp.v1.sch");
  public static final String FK7210_PDF_PATH = "fk7210/pdf/fk7210_v1.pdf";
  public static final String FK7472_PDF_PATH = "fk7472/pdf/fk7472_v1.pdf";
  public static final String FK7210_NAME = "Intyg om graviditet";
  public static final String FK7472_NAME = "Intyg om tillfällig föräldrapenning";
  public static final Recipient FK7210_RECIPIENT = new Recipient(
      new RecipientId("FKASSA"), "Försäkringskassan");
}
