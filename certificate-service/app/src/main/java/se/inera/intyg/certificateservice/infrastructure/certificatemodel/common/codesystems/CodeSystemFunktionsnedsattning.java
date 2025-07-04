package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemFunktionsnedsattning {

  public static final String CODE_SYSTEM = "FUNKTIONSNEDSATTNING";

  private CodeSystemFunktionsnedsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static final Code INTELLEKTUELL_FUNKTION = new Code(
      "INTELLEKTUELL_FUNKTION",
      CODE_SYSTEM,
      "Intellektuell funktion"
  );

  public static final Code KOMMUNIKATION_SOCIAL_INTERAKTION = new Code(
      "KOMMUNIKATION_SOCIAL_INTERAKTION",
      CODE_SYSTEM, "Övergripande psykosociala funktioner"
  );

  public static final Code UPPMARKSAMHET = new Code(
      "UPPMARKSAMHET",
      CODE_SYSTEM,
      "Uppmärksamhet, koncentration och exekutiv funktion"
  );

  public static final Code PSYKISK_FUNKTION = new Code(
      "PSYKISK_FUNKTION",
      CODE_SYSTEM,
      "Annan psykisk funktion"
  );

  public static final Code HORSELFUNKTION = new Code(
      "HORSELFUNKTION",
      CODE_SYSTEM,
      "Hörselfunktion"
  );

  public static final Code SYNFUNKTION = new Code(
      "SYNFUNKTION",
      CODE_SYSTEM,
      "Synfunktion"
  );

  public static final Code SINNESFUNKTION_V1 = new Code(
      "Sinnesfunktion",
      CODE_SYSTEM,
      "Övriga sinnesfunktioner och smärta"
  );

  public static final Code SINNESFUNKTION_V2 = new Code(
      "Sinnesfunktion",
      CODE_SYSTEM,
      "Sinnesfunktioner och smärta"
  );

  public static final Code KOORDINATION = new Code(
      "KOORDINATION",
      CODE_SYSTEM,
      "Balans, koordination och motorik"
  );

  public static final Code ANNAN_KROPPSILIG_FUNKTION = new Code(
      "ANNAN_KROPPSILIG_FUNKTION",
      CODE_SYSTEM,
      "Annan kroppslig funktion"
  );

  public static final Code ANDNINGS_FUNKTION = new Code(
      "ANDNINGS_FUNKTION",
      CODE_SYSTEM,
      "Andningsfunktion"
  );
}