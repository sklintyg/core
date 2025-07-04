package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemAktivitetsbegransning {

  public static final String CODE_SYSTEM = "AKTIVITETSBEGRANSNING";

  private CodeSystemAktivitetsbegransning() {
    throw new IllegalStateException("Utility class");
  }

  public static final Code LARANDE_BEGRANSNING = new Code(
      "LARANDE_BEGRANSNING",
      CODE_SYSTEM,
      "Lärande, tillämpa kunskap samt allmänna uppgifter och krav"
  );

  public static final Code KOMMUNIKATION_BEGRANSNING = new Code(
      "KOMMUNIKATION_BEGRANSNING",
      CODE_SYSTEM,
      "Kommunikation"
  );

  public static final Code FORFLYTTNING_BEGRANSNING = new Code(
      "FORFLYTTNING_BEGRANSNING",
      CODE_SYSTEM,
      "Förflyttning"
  );

  public static final Code PERSONLIG_VARD_BEGRANSNING = new Code(
      "PERSONLIG_VARD_BEGRANSNING",
      CODE_SYSTEM,
      "Personlig vård och sköta sin hälsa"
  );

  public static final Code OVRIGA_BEGRANSNING = new Code(
      "OVRIGA_BEGRANSNING",
      CODE_SYSTEM,
      "Övriga aktivitetsbegränsningar"
  );

}
