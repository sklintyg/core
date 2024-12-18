package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKorrigeringAvSynskarpa {

  public static final String CODE_SYSTEM = "KORRIGERING_AV_SYNSKARPA";

  private CodeSystemKorrigeringAvSynskarpa() {
    throw new IllegalStateException("Utility class");
  }

  public static final Code GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER = new Code(
      "6.1",
      CODE_SYSTEM,
      "Glasögon och inget av glasen har en styrka över plus 8 dioptrier i den mest brytande meridianen"
  );

  public static final Code GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER = new Code(
      "6.3",
      CODE_SYSTEM,
      "Glasögon och något av glasen har en styrka över plus 8 dioptrier i den mest brytande meridianen"
  );

  public static final Code KONTAKTLINSER = new Code(
      "6.5",
      CODE_SYSTEM,
      "Kontaktlinser"
  );

}