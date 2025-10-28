package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

public class TestDataCertificateModelConstants {

  private TestDataCertificateModelConstants() {

  }

  public static final CertificateType FK7210_TYPE = new CertificateType("fk7210");
  public static final CertificateType FK3226_TYPE = new CertificateType("fk3226");
  public static final CertificateType FK7472_TYPE = new CertificateType("fk7472");
  public static final CertificateType FK7809_TYPE = new CertificateType("fk7809");
  public static final CertificateType FK7427_TYPE = new CertificateType("fk7427");
  public static final CertificateType FK7426_TYPE = new CertificateType("fk7426");
  public static final CertificateType FK3221_TYPE = new CertificateType("fk3221");
  public static final CertificateType FK7810_TYPE = new CertificateType("fk7810");
  public static final CertificateType FK7804_TYPE = new CertificateType("fk7804");
  public static final CertificateType AG7804_TYPE = new CertificateType("ag7804");
  public static final CertificateType AG114_TYPE = new CertificateType("ag114");
  public static final CertificateVersion FK7210_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK3226_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7472_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7427_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7809_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7426_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK3221_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7810_VERSION = new CertificateVersion("1.0");
  public static final CertificateVersion FK7804_VERSION = new CertificateVersion("2.0");
  public static final CertificateVersion AG7804_VERSION = new CertificateVersion("2.0");
  public static final CertificateVersion AG114_VERSION = new CertificateVersion("2.0");
  public static final CertificateTypeName FK7210_TYPE_NAME = new CertificateTypeName("FK7210");
  public static final CertificateTypeName FK3226_TYPE_NAME = new CertificateTypeName("FK3226");
  public static final CertificateTypeName FK7472_TYPE_NAME = new CertificateTypeName("FK7472");
  public static final CertificateTypeName FK7809_TYPE_NAME = new CertificateTypeName("FK7809");
  public static final CertificateTypeName FK7427_TYPE_NAME = new CertificateTypeName("FK7427");
  public static final CertificateTypeName FK7426_TYPE_NAME = new CertificateTypeName("FK7426");
  public static final CertificateTypeName FK3221_TYPE_NAME = new CertificateTypeName("FK3221");
  public static final CertificateTypeName FK7810_TYPE_NAME = new CertificateTypeName("FK7810");
  public static final CertificateTypeName FK7804_TYPE_NAME = new CertificateTypeName("FK7804");
  public static final CertificateTypeName AG7804_TYPE_NAME = new CertificateTypeName("AG7804");
  public static final CertificateTypeName AG114_TYPE_NAME = new CertificateTypeName("AG1-14");
  public static final CertificateModelId FK7210_ID = CertificateModelId.builder()
      .type(FK7210_TYPE)
      .version(FK7210_VERSION)
      .build();
  public static final CertificateModelId FK7804_ID = CertificateModelId.builder()
      .type(FK7804_TYPE)
      .version(FK7804_VERSION)
      .build();
  public static final Code FK7210_CODE_TYPE = new Code(
      "IGRAV",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om graviditet"
  );
  public static final Code FK3226_CODE_TYPE = new Code(
      "LUNSP",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om graviditet"
  );
  public static final Code FK7472_CODE_TYPE = new Code(
      "ITFP",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Intyg om tillfällig föräldrapenning"
  );
  public static final Code FK7809_CODE_TYPE = new Code(
      "LUMEK",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarutlåtande för merkostnadsersättning"
  );
  public static final Code FK7427_CODE_TYPE = new Code(
      "LU_TFP_B12_16",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarutlåtande tillfällig föräldrapenning barn 12–16 år"
  );
  public static final Code FK7426_CODE_TYPE = new Code(
      "LU_TFP_ASB18",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18"
  );
  public static final Code FK3221_CODE_TYPE = new Code(
      "LU_OMV_MEK",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning"
  );
  public static final Code FK7810_CODE_TYPE = new Code(
      "LUAS",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarutlåtande för assistansersättning"
  );
  public static final Code FK7804_CODE_TYPE = new Code(
      "LISJP",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarintyg för sjukpenning"
  );
  public static final Code AG7804_CODE_TYPE = new Code(
      "AG7804",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarintyg om arbetsförmåga – arbetsgivaren"
  );
  public static final Code AG114_CODE_TYPE = new Code(
      "AG114",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      "Läkarintyg om arbetsförmåga – sjuklöneperioden"
  );

  public static final SchematronPath FK7210_SCHEMATRON_PATH = new SchematronPath(
      "fk7210/schematron/igrav.v1.sch");
  public static final SchematronPath FK3226_SCHEMATRON_PATH = new SchematronPath(
      "fk3226/schematron/lunsp.v1.sch");
  public static final SchematronPath FK7472_SCHEMATRON_PATH = new SchematronPath(
      "fk7472/schematron/itfp.v1.sch");
  public static final SchematronPath FK7809_SCHEMATRON_PATH = new SchematronPath(
      "fk7809/schematron/lumek.v1.sch");
  public static final SchematronPath FK7426_SCHEMATRON_PATH = new SchematronPath(
      "fk7426/schematron/lu_tfp_b12_16.v1.sch"
  );
  public static final SchematronPath FK3221_SCHEMATRON_PATH = new SchematronPath(
      "fk3221/schematron/lu_omv_mek.v1.sch"
  );
  public static final SchematronPath FK7810_SCHEMATRON_PATH = new SchematronPath(
      "fk7810/schematron/luas.v1.sch"
  );
  public static final SchematronPath FK7804_SCHEMATRON_PATH = new SchematronPath(
      "fk7804/schematron/lisjp.v2.sch"
  );

  public static final String FK7210_PDF_PATH = "fk7210/pdf/fk7210_v1.pdf";
  public static final String FK3226_PDF_PATH = "fk3226/pdf/fk3226_v1.pdf";
  public static final String FK7210_PDF_PATH_NO_ADDRESS = "fk7210/pdf/fk7210_v1_no_address.pdf";
  public static final String FK3226_PDF_PATH_NO_ADDRESS = "fk3226/pdf/fk3226_v1_no_address.pdf";
  public static final String FK7210_NAME = "Intyg om graviditet";
  public static final String FK3226_NAME = "Läkarutlåtande för närståendepenning";
  public static final String FK7472_NAME = "Intyg om tillfällig föräldrapenning";
  public static final String FK7809_NAME = "Läkarutlåtande för merkostnadsersättning";
  public static final String FK7427_NAME = "Läkarutlåtande tillfällig föräldrapenning barn 12–16 år";
  public static final String FK7426_NAME = "Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18";
  public static final String FK3221_NAME = "Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning";
  public static final String FK7810_NAME = "Läkarutlåtande för assistansersättning";
  public static final String FK7804_NAME = "Läkarintyg för sjukpenning";
  public static final String AG7804_NAME = "Läkarintyg om arbetsförmåga – arbetsgivaren";
  public static final String AG114_NAME = "Läkarintyg om arbetsförmåga – sjuklöneperioden";
  public static final Recipient FK_RECIPIENT = new Recipient(
      new RecipientId("FKASSA"), "Försäkringskassan", "Logisk adress"
  );
  public static Recipient SKR_RECIPIENT = new Recipient(
      new RecipientId("SKR"), "Sveriges Kommuner och Regioner", null
  );
}