package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvTs0001 {

  public static final String CODE_SYSTEM = "KV_TS_0001";

  public static final Code JOURNALUPPGIFTER = new Code(
      "JOURNALUPPGIFTER",
      CODE_SYSTEM,
      "Journaluppgifter"
  );

  public static final Code DISTANSKONTAKT = new Code(
      "DISTANSKONTAKT",
      CODE_SYSTEM,
      "Distanskontakt"
  );

  public static final Code UNDERSOKNING = new Code(
      "UNDERSOKNING",
      CODE_SYSTEM,
      "Undersökning av personen"
  );

  private CodeSystemKvTs0001() {
    throw new IllegalStateException("Utility class");
  }
}
