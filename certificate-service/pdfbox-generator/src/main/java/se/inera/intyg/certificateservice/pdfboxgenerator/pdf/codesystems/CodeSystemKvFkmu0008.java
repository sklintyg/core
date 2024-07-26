package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0008 {

  public static final String CODE_SYSTEM = "KV_FKMU_0008";

  public static final Code EN_ATTONDEL = new Code(
      "EN_ATTONDEL",
      CODE_SYSTEM,
      "En åttondel av den ordinarie tiden"
  );

  public static final Code EN_FJARDEDEL = new Code(
      "EN_FJARDEDEL",
      CODE_SYSTEM,
      "En fjärdedel av den ordinarie tiden"
  );

  public static final Code HALVA = new Code(
      "HALVA",
      CODE_SYSTEM,
      "Halva den ordinarie tiden"
  );

  public static final Code TRE_FJARDEDELAR = new Code(
      "TRE_FJARDEDELAR",
      CODE_SYSTEM,
      "Tre fjärdedelar av den ordinarie tiden"
  );

  public static final Code HELA = new Code(
      "HELA",
      CODE_SYSTEM,
      "Hela den ordinarie tiden"
  );

  private CodeSystemKvFkmu0008() {
    throw new IllegalStateException("Utility class");
  }
}
