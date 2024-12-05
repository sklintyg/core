package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvTs0002 {

  public static final String CODE_SYSTEM = "KV_TS_0002";

  public static final Code G2 = new Code(
      "G2",
      CODE_SYSTEM,
      "Körkortstillstånd grupp II (C1, C1E, C, CE)"
  );

  public static final Code G23 = new Code(
      "G23",
      CODE_SYSTEM,
      "Körkortstillstånd grupp II och III (C1, C1E, C, CE, D1, D1E, D, DE)"
  );

  public static final Code GH2 = new Code(
      "GH2",
      CODE_SYSTEM,
      "Förlängning av högre behörighet II (C1, C1E, C, CE)"
  );

  public static final Code GH23 = new Code(
      "GH23",
      CODE_SYSTEM,
      "Förlängning av högre behörighet II och III (C1, C1E, C, CE, D1, D1E, D, DE)"
  );

  public static final Code UTLANDSKT = new Code(
      "UTLANDSKT",
      CODE_SYSTEM,
      "Utbyte av utländskt körkort"
  );

  public static final Code TAXI = new Code(
      "TAXI",
      CODE_SYSTEM,
      "Taxiförarlegitimation"
  );

  public static final Code ANNAT = new Code(
      "ANNAT",
      CODE_SYSTEM,
      "Intyg i annat fall på begäran av Transportstyrelsen"
  );

  private CodeSystemKvTs0002() {
    throw new IllegalStateException("Utility class");
  }
}
