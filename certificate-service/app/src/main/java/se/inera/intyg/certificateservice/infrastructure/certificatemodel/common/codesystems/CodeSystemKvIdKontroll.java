package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvIdKontroll {

  public static final String CODE_SYSTEM = "e7cc8f30-a353-4c42-b17a-a189b6876647";

  public static final Code IDK1 = new Code(
      "IDK1",
      CODE_SYSTEM,
      "ID-kort"
  );

  public static final Code IDK2 = new Code(
      "IDK2",
      CODE_SYSTEM,
      "Företagskort eller tjänstekort"
  );

  public static final Code IDK3 = new Code(
      "IDK3",
      CODE_SYSTEM,
      "Svenskt körkort"
  );

  public static final Code IDK4 = new Code(
      "IDK4",
      CODE_SYSTEM,
      "Personlig kännedom"
  );

  public static final Code IDK5 = new Code(
      "IDK5",
      CODE_SYSTEM,
      "Försäkran enligt 18 kap. 4 §"
  );

  public static final Code IDK6 = new Code(
      "IDK6",
      CODE_SYSTEM,
      "Pass"
  );

  private CodeSystemKvIdKontroll() {
    throw new IllegalStateException("Utility class");
  }
}
