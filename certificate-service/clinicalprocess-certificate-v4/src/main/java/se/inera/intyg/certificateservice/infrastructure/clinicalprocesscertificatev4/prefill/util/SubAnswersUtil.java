package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util;

import java.util.List;
import java.util.stream.Stream;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
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

  public static String getContent(List<Delsvar> subAnswers, List<Svar> answers,
      ElementConfiguration configuration) {
    if (!subAnswers.isEmpty()) {
      return (String) subAnswers
          .stream()
          .filter(subAnswer -> subAnswer.getId().equals(configuration.id().value()))
          .toList()
          .getFirst().getContent().getFirst();
    }
    return (String) answers
        .getFirst()
        .getDelsvar()
        .stream()
        .filter(subAnswer -> subAnswer.getId().equals(configuration.id().value()))
        .toList()
        .getFirst()
        .getContent()
        .getFirst();
  }
}