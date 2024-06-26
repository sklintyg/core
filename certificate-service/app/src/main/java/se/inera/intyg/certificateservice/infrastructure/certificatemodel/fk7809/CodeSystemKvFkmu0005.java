package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeSystemKvFkmu0005 {

  public static final String CODE_SYSTEM = "KV_FKMU_0005";

  public static List<Code> getAllCodes() {
    return Stream.of(
            NEUROPSYKIATRISKT,
            HABILITERING,
            ARBETSTERAPEUT,
            FYSIOTERAPEUT,
            LOGOPED,
            PSYKOLOG,
            SPECIALISTKLINIK,
            VARD_UTOMLANDS,
            OVRIGT,
            HORSELHABILITERING,
            SYNHABILITERING,
            AUDIONOM,
            DIETIST,
            ORTOPTIST,
            ORTOPEDTEKNIKER)
        .sorted(Comparator.comparing(Code::displayName))
        .toList();
  }

  public static final Code NEUROPSYKIATRISKT = new Code(
      "NEUROPSYKIATRISKT",
      CODE_SYSTEM,
      "Neuropsykiatriskt utlåtande"
  );

  public static final Code HABILITERING = new Code(
      "HABILITERING",
      CODE_SYSTEM,
      "Underlag från habiliteringen"
  );

  public static final Code ARBETSTERAPEUT = new Code(
      "ARBETSTERAPEUT",
      CODE_SYSTEM,
      "Underlag från arbetsterapeut"
  );

  public static final Code FYSIOTERAPEUT = new Code(
      "FYSIOTERAPEUT",
      CODE_SYSTEM,
      "Underlag från fysioterapeut"
  );

  public static final Code LOGOPED = new Code(
      "LOGOPED",
      CODE_SYSTEM,
      "Underlag från logoped"
  );

  public static final Code PSYKOLOG = new Code(
      "PSYKOLOG",
      CODE_SYSTEM,
      "Underlag från psykolog"
  );

  public static final Code SPECIALISTKLINIK = new Code(
      "SPECIALISTKLINIK",
      CODE_SYSTEM,
      "Utredning av annan specialistklinik"
  );

  public static final Code VARD_UTOMLANDS = new Code(
      "VARD_UTOMLANDS",
      CODE_SYSTEM,
      "Utredning från vårdinrättning utomlands"
  );

  public static final Code OVRIGT = new Code(
      "OVRIGT",
      CODE_SYSTEM,
      "Övrigt"
  );

  public static final Code HORSELHABILITERING = new Code(
      "HORSELHABILITERING",
      CODE_SYSTEM,
      "Underlag från hörselhabiliteringen"
  );

  public static final Code SYNHABILITERING = new Code(
      "SYNHABILITERING",
      CODE_SYSTEM,
      "Underlag från synhabiliteringen"
  );

  public static final Code AUDIONOM = new Code(
      "AUDIONOM",
      CODE_SYSTEM,
      "Underlag från audionom"
  );

  public static final Code DIETIST = new Code(
      "DIETIST",
      CODE_SYSTEM,
      "Underlag från dietist"
  );

  public static final Code ORTOPTIST = new Code(
      "ORTOPTIST",
      CODE_SYSTEM,
      "Underlag från ortoptist"
  );

  public static final Code ORTOPEDTEKNIKER = new Code(
      "ORTOPEDTEKNIKER",
      CODE_SYSTEM,
      "Underlag från ortopedtekniker eller ortopedingenjör"
  );

  private CodeSystemKvFkmu0005() {
    throw new IllegalStateException("Utility class");
  }
}
