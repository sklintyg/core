package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0002 {

  public static final String CODE_SYSTEM = "KV_FKMU_0002";

  public static final Code NUVARANDE_ARBETE = new Code(
      "NUVARANDE_ARBETE",
      CODE_SYSTEM,
      "Nuvarande arbete"
  );

  public static final Code ARBETSSOKANDE = new Code(
      "ARBETSSOKANDE",
      CODE_SYSTEM,
      "Arbetssökande - att utföra sådant arbete som är normalt förekommande på arbetsmarknaden"
  );

  public static final Code FORALDRALEDIG = new Code(
      "FORALDRALEDIG",
      CODE_SYSTEM,
      "Föräldraledighet"
  );

  public static final Code STUDIER = new Code(
      "STUDIER",
      CODE_SYSTEM,
      "Studier"
  );

  private CodeSystemKvFkmu0002() {
    throw new IllegalStateException("Utility class");
  }
}

