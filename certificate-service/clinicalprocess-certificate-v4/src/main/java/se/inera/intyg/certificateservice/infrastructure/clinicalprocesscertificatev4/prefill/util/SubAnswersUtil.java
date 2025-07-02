package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util;

import java.util.List;
import java.util.stream.Stream;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class SubAnswersUtil {

  private SubAnswersUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Delsvar> get(List<Svar> answers, List<Delsvar> subAnswers) {
    return Stream.concat(
            answers.stream()
                .map(Svar::getDelsvar)
                .flatMap(List::stream),
            subAnswers.stream()
        )
        .toList();
  }
}