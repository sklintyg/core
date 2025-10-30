package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvIntygstyp {

  public static final String CODE_SYSTEM = "b64ea353-e8f6-4832-b563-fc7d46f29548";

  public static final Code AG114 = new Code(
      "AG1-14",
      CODE_SYSTEM,
      "Läkarintyg om arbetsförmåga – sjuklöneperiod"
  );

  public static final Code AG7804 = new Code(
      "AG7804",
      CODE_SYSTEM,
      "Läkarintyg om arbetsförmåga – arbetsgivare"
  );

  public static final Code FK3221 = new Code(
      "LU_OMV_MEK",
      CODE_SYSTEM,
      "Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning"
  );

  public static final Code FK3226 = new Code(
      "LUNSP",
      CODE_SYSTEM,
      "Läkarutlåtande för närståendepenning"
  );

  public static final Code FK7210 = new Code(
      "IGRAV",
      CODE_SYSTEM,
      "Intyg om graviditet"
  );

  public static final Code FK7426 = new Code(
      "LU_TFP_ASB18",
      CODE_SYSTEM,
      "Läkarutlåtande Tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18"
  );

  public static final Code FK7427 = new Code(
      "LU_TFP_B12_16",
      CODE_SYSTEM,
      "Läkarutlåtande Tillfällig föräldrapenning Barn 12-16 år"
  );

  public static final Code FK7472 = new Code(
      "ITFP",
      CODE_SYSTEM,
      "Intyg tillfällig föräldrapenning"
  );

  public static final Code FK7804 = new Code(
      "LISJP",
      CODE_SYSTEM,
      "Läkarintyg sjukpenning"
  );

  public static final Code FK7809 = new Code(
      "LUMEK",
      CODE_SYSTEM,
      "Läkarutlåtande för merkostnadsersättning"
  );

  public static final Code FK7810 = new Code(
      "LUAS",
      CODE_SYSTEM,
      "Läkarutlåtande för assistansersättning"
  );

  public static final Code TS8071 = new Code(
      "TS8071",
      CODE_SYSTEM,
      "Läkarintyg- avseende högre körkortsbehörigheter eller taxiförarlegitimation- på begäran från Transportstyrelsen"
  );

  private CodeSystemKvIntygstyp() {
    throw new IllegalStateException("Utility class");
  }
}


