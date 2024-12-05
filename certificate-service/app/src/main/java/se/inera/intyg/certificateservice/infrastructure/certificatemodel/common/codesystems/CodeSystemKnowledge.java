package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKnowledge {

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

  public static final Code NO_KNOWLEDGE = new Code(
      "VET_EJ",
      CODE_SYSTEM,
      "Vet ej"
  );


  private CodeSystemKnowledge() {
    throw new IllegalStateException("Utility class");
  }
}
