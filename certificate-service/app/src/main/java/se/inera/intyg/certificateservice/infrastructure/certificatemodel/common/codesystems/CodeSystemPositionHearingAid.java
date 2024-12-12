package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemPositionHearingAid {

  public static final String CODE_SYSTEM = "CODE_SYSTEM_POSITION_HEARING_AID";

  public static final Code HOGER = new Code(
      "89644007",
      CODE_SYSTEM,
      "Höger"
  );

  public static final Code VANSTER = new Code(
      "25577004",
      CODE_SYSTEM,
      "Vänster"
  );

  public static final Code BADA_ORONEN = new Code(
      "34338003",
      CODE_SYSTEM,
      "Båda öronen"
  );


  private CodeSystemPositionHearingAid() {
    throw new IllegalStateException("Utility class");
  }
}
