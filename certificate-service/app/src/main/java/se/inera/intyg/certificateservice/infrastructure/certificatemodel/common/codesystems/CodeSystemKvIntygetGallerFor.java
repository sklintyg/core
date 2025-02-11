package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvIntygetGallerFor {

  public static final String CODE_SYSTEM = "1.2.752.129.5.1.66";

  public static final Code GR_II = new Code(
      "gr_II",
      CODE_SYSTEM,
      "Körkortstillstånd grupp II (C1, C1E, C, CE)"
  );

  public static final Code GR_II_III = new Code(
      "gr_II_III",
      CODE_SYSTEM,
      "Körkortstillstånd grupp II och III (C1, C1E, C, CE, D1, D1E, D, DE)"
  );

  public static final Code FORLANG_GR_II = new Code(
      "forlang_gr_II",
      CODE_SYSTEM,
      "Förlängning av högre behörighet II (C1, C1E, C, CE)"
  );

  public static final Code FORLANG_GR_II_III = new Code(
      "forlang_gr_II_III",
      CODE_SYSTEM,
      "Förlängning av högre behörighet II och III (C1, C1E, C, CE, D1, D1E, D, DE)"
  );

  public static final Code UTLANDSKT = new Code(
      "utbyt_utl_kk",
      CODE_SYSTEM,
      "Utbyte av utländskt körkort"
  );

  public static final Code TAXI = new Code(
      "tax_leg",
      CODE_SYSTEM,
      "Ansökan om taxiförarlegitimation"
  );

  public static final Code ANNAT = new Code(
      "int_begar_ts",
      CODE_SYSTEM,
      "Intyg i annat fall på begäran av Transportstyrelsen"
  );

  private CodeSystemKvIntygetGallerFor() {
    throw new IllegalStateException("Utility class");
  }
}
