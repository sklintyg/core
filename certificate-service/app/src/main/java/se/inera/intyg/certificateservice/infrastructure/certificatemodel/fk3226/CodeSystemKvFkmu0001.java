package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0001 {

  public static final String CODE_SYSTEM = "KV_FKMU_0001";

  public static final Code UNDERSOKNING = new Code(
      "UNDERSOKNING",
      CODE_SYSTEM,
      "min undersökning av patienten"
  );

  public static final Code JOURNALUPGIFTER = new Code(
      "JOURNALUPPGIFTER",
      CODE_SYSTEM,
      "journaluppgifter från den"
  );

  public static final Code ANNAT = new Code(
      "ANNAT",
      CODE_SYSTEM,
      "annat"
  );

  public static final Code ANHORIG = new Code(
      "ANHORIG",
      CODE_SYSTEM,
      "Anhörig eller annans relation till patienten"
  );

  private CodeSystemKvFkmu0001() {
    throw new IllegalStateException("Utility class");
  }
}
