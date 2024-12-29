package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvInformationskallaForIntyg {

  public static final String CODE_SYSTEM = "1.2.752.129.5.1.67";

  public static final Code JOURNALUPPGIFTER = new Code(
      "journal",
      CODE_SYSTEM,
      "Journaluppgifter"
  );

  public static final Code DISTANSKONTAKT = new Code(
      "distkont",
      CODE_SYSTEM,
      "Distanskontakt"
  );

  public static final Code UNDERSOKNING = new Code(
      "undersokn",
      CODE_SYSTEM,
      "Undersökning av personen"
  );

  private CodeSystemKvInformationskallaForIntyg() {
    throw new IllegalStateException("Utility class");
  }
}
