package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvAnatomiskLokalisationHorapparat {

  public static final String CODE_SYSTEM = "1.2.752.129.5.1.68";

  public static final Code HOGER = new Code(
      "25577004",
      CODE_SYSTEM,
      "Höger"
  );

  public static final Code VANSTER = new Code(
      "89644007",
      CODE_SYSTEM,
      "Vänster"
  );

  public static final Code BADA_ORONEN = new Code(
      "34338003",
      CODE_SYSTEM,
      "Båda öronen"
  );


  private CodeSystemKvAnatomiskLokalisationHorapparat() {
    throw new IllegalStateException("Utility class");
  }
}
