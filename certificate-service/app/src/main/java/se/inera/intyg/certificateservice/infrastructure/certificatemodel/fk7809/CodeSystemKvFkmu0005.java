package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0005 {

  public static final String CODE_SYSTEM = "KV_FKMU_0005";

  public static final Code NEUROPSYKIATRISKT = new Code(
      "NEUROPSYKIATRISKT",
      CODE_SYSTEM,
      "Neuropsykiatriskt utlåtande"
  );

  public static final Code HABILITERING = new Code(
      "HABILITERING",
      CODE_SYSTEM,
      "Habiliteringen (underlag)"
  );

  public static final Code ARBETSTERAPEUT = new Code(
      "ARBETSTERAPEUT",
      CODE_SYSTEM,
      "Arbetsterapeut (underlag)"
  );

  public static final Code FYSIOTERAPEUT = new Code(
      "FYSIOTERAPEUT",
      CODE_SYSTEM,
      "Fysioterapeut (underlag)"
  );

  public static final Code LOGOPED = new Code(
      "LOGOPED",
      CODE_SYSTEM,
      "Logoped (underlag)"
  );

  public static final Code PSYKOLOG = new Code(
      "PSYKOLOG",
      CODE_SYSTEM,
      "Psykolog (underlag)"
  );

  public static final Code SPECIALISTKLINIK = new Code(
      "SPECIALISTKLINIK",
      CODE_SYSTEM,
      "Annan specialistklinik (utredning)"
  );

  public static final Code VARD_UTOMLANDS = new Code(
      "VARD_UTOMLANDS",
      CODE_SYSTEM,
      "Vårdinrättning utomlands (utredning)"
  );
  //TODO dubbelkolla skillnaden mellan övrigt och övrigt utlåtande. En heter OVRIGT_UTLATANDE och en annan ÖVRIGT
  public static final Code OVRIGT_UTLATANDE = new Code(
      "OVRIGT_UTLATANDE",
      CODE_SYSTEM,
      "Övrigt"
  );

  public static final Code HORSELHABILITERING = new Code(
      "HORSELHABILITERING",
      CODE_SYSTEM,
      "Hörselhabiliteringen (underlag)"
  );

  public static final Code SYNHABILITERING = new Code(
      "SYNHABILITERING",
      CODE_SYSTEM,
      "Synhabiliteringen (utredning)"
  );

  public static final Code AUDIONOM = new Code(
      "AUDIONOM",
      CODE_SYSTEM,
      "Audionom (underlag)"
  );

  public static final Code DIETIST = new Code(
      "DIETIST",
      CODE_SYSTEM,
      "Dietist (underlag)"
  );

  public static final Code ORTOPTIST = new Code(
      "ORTOPTIST",
      CODE_SYSTEM,
      "Ortoptist (underlag)"
  );

  public static final Code ORTOPEDTEKNIKER = new Code(
      "ORTOPEDTEKNIKER",
      CODE_SYSTEM,
      "Ortopedtekniker eller ortopedingenjör (underlag)"
  );

  private CodeSystemKvFkmu0005() {
    throw new IllegalStateException("Utility class");
  }
}
