package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemDecision {

  public static final String CODE_SYSTEM = "CODE_SYSTEM_KNOWLEDGE";

  public static final Code YES = new Code(
      "JA",
      CODE_SYSTEM,
      "Ja"
  );

  public static final Code NO = new Code(
      "NEJ",
      CODE_SYSTEM,
      "Nej"
  );

  public static final Code NO_DECISION = new Code(
      "KAN_INTE_TA_STALLNING",
      CODE_SYSTEM,
      "Kan inte ta ställning"
  );


  private CodeSystemDecision() {
    throw new IllegalStateException("Utility class");
  }
}
