package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvTs001 {

  public static final String CODE_SYSTEM = "TS-001";

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

  public static final Code NO_KNOWLEDGE = new Code(
      "vetej",
      CODE_SYSTEM,
      "Vet ej"
  );

  public static final Code NO_KNOWLEDGE_V2 = new Code(
      "vetinte",
      CODE_SYSTEM,
      "Vet inte"
  );


  private CodeSystemKvTs001() {
    throw new IllegalStateException("Utility class");
  }
}
