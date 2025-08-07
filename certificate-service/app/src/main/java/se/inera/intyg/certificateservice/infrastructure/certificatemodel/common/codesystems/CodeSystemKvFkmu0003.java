package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0003 {

  public static final String CODE_SYSTEM = "KV_FKMU_0003";

  public static final Code HELT_NEDSATT = new Code(
      "HELT_NEDSATT",
      CODE_SYSTEM,
      "100 procent"
  );

  public static final Code TRE_FJARDEDEL = new Code(
      "TRE_FJARDEDEL",
      CODE_SYSTEM,
      "75 procent"
  );

  public static final Code HALFTEN = new Code(
      "HALFTEN",
      CODE_SYSTEM,
      "50 procent"
  );

  public static final Code EN_FJARDEDEL = new Code(
      "EN_FJARDEDEL",
      CODE_SYSTEM,
      "25 procent"
  );

  private CodeSystemKvFkmu0003() {
    throw new IllegalStateException("Utility class");
  }
}

