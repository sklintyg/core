package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvTs001 {

  public static final String CODE_SYSTEM = "TS-001";

  public static final Code JA = new Code(
      "ja",
      CODE_SYSTEM,
      "Ja"
  );

  public static final Code NEJ = new Code(
      "nej",
      CODE_SYSTEM,
      "Nej"
  );

  public static final Code VET_EJ = new Code(
      "vetej",
      CODE_SYSTEM,
      "Vet ej"
  );

  public static final Code VET_INTE = new Code(
      "vetinte",
      CODE_SYSTEM,
      "Vet inte"
  );


  private CodeSystemKvTs001() {
    throw new IllegalStateException("Utility class");
  }
}
