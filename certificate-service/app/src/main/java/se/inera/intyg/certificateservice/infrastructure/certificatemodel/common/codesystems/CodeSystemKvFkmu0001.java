package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0001 {

  public static final String CODE_SYSTEM = "KV_FKMU_0001";

  public static final Code UNDERSOKNING = new Code(
      "UNDERSOKNING",
      CODE_SYSTEM,
      "min undersökning av patienten"
  );

  public static final Code JOURNALUPPGIFTER = new Code(
      "JOURNALUPPGIFTER",
      CODE_SYSTEM,
      "journaluppgifter från den"
  );

  public static final Code ANNAT = new Code(
      "ANNAT",
      CODE_SYSTEM,
      "annat"
  );

  public static final Code ANHORIG_V1 = new Code(
      "ANHORIG",
      CODE_SYSTEM,
      "anhörig eller annans beskrivning av patienten"
  );

  public static final Code ANHORIG_V2 = new Code(
      "ANHORIG",
      CODE_SYSTEM,
      "anhörigs eller annans beskrivning av patienten"
  );

  public static final Code FYSISKUNDERSOKNING = new Code(
      "FYSISKUNDERSOKNING",
      CODE_SYSTEM,
      "min undersökning vid fysiskt vårdmöte"
  );

  public static final Code DIGITALUNDERSOKNING = new Code(
      "DIGITALUNDERSOKNING",
      CODE_SYSTEM,
      "min undersökning vid digitalt vårdmöte"
  );

  public static final Code TELEFONKONTAKT = new Code(
      "TELEFONKONTAKT",
      CODE_SYSTEM,
      "min telefonkontakt med patienten"
  );

  public static final Code FORALDER = new Code(
      "FORALDER",
      CODE_SYSTEM,
      "förälders beskrivning av barnet"
  );

  private CodeSystemKvFkmu0001() {
    throw new IllegalStateException("Utility class");
  }
}
