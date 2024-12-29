package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvTs002 {

  public static final String CODE_SYSTEM = "TS-002";

  public static final Code YES = new Code(
      "ja",
      CODE_SYSTEM,
      "Ja"
  );

  public static final Code NO = new Code(
      "nej",
      CODE_SYSTEM,
      "Nej"
  );

  public static final Code NO_DECISION = new Code(
      "ejstalln",
      CODE_SYSTEM,
      "Kan inte ta ställning"
  );


  private CodeSystemKvTs002() {
    throw new IllegalStateException("Utility class");
  }
}
