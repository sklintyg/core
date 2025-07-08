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

  public static final Code INGEN_TIDIGARE = new Code(
      "INGEN_TIDIGARE",
      CODE_SYSTEM,
      "Ingen tidigare kännedom"
  );

  public static final Code MINDRE_AN_ETT_AR = new Code(
      "MINDRE_AN_ETT_AR",
      CODE_SYSTEM,
      "Kännedom i mindre än ett år)"
  );

  public static final Code MER_AN_ETT_AR = new Code(
      "MER_AN_ETT_AR",
      CODE_SYSTEM,
      "Kännedom i mer än ett år"
  );

  private CodeSystemKvFkmu0010() {
    throw new IllegalStateException("Utility class");
  }
}
