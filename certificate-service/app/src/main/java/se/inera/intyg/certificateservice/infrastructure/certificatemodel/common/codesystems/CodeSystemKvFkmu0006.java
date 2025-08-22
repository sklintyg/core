package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0006 {

  public static final String CODE_SYSTEM = "KV_FKMU_0006";

  public static final Code STOR_SANNOLIKHET = new Code(
      "STOR_SANNOLIKHET",
      CODE_SYSTEM,
      "Patienten förväntas kunna återgå helt i nuvarande sysselsättning efter detta intygs slutdatum"
  );

  public static final Code ATER_X_ANTAL_MANADER = new Code(
      "ATER_X_ANTAL_MANADER",
      CODE_SYSTEM,
      "Patienten förväntas kunna återgå helt i nuvarande sysselsättning efter angiven tid"
  );

  public static final Code SANNOLIKT_INTE = new Code(
      "SANNOLIKT_INTE",
      CODE_SYSTEM,
      "Patienten förväntas inte kunna återgå helt i nuvarande sysselsättning"
  );

  public static final Code PROGNOS_OKLAR = new Code(
      "PROGNOS_OKLAR",
      CODE_SYSTEM,
      "Prognosen för återgång i nuvarande sysselsättning är svårbedömd"
  );

  private CodeSystemKvFkmu0006() {
    throw new IllegalStateException("Utility class");
  }
}
