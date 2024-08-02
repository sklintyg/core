package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0010 {

  public static final String CODE_SYSTEM = "KV_FKMU_0010";

  public static final Code ENDAST_PALLIATIV = new Code(
      "ENDAST_PALLIATIV",
      CODE_SYSTEM,
      "Endast palliativ vård ges och all aktiv behandling mot sjukdomstillståndet har avslutats"
  );

  public static final Code AKUT_LIVSHOTANDE = new Code(
      "AKUT_LIVSHOTANDE",
      CODE_SYSTEM,
      "Akut livshotande tillstånd (till exempel vård på intensivvårdsavdelning)"
  );

  public static final Code ANNAT = new Code(
      "ANNAT",
      CODE_SYSTEM,
      "Annat"
  );

  private CodeSystemKvFkmu0010() {
    throw new IllegalStateException("Utility class");
  }
}
