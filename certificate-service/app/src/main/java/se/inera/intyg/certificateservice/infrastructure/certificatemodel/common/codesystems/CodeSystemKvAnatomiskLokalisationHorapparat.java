package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvAnatomiskLokalisationHorapparat {

  public static final String CODE_SYSTEM = "kv_anatomisk_lokalisation_horapparat";

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


  private CodeSystemKvAnatomiskLokalisationHorapparat() {
    throw new IllegalStateException("Utility class");
  }
}
