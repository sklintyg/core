package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class PrefillValidator {

  private PrefillValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static PrefillError validateSingleAnswerOrSubAnswer(List<Svar> answers,
      List<Delsvar> subAnswers,
      ElementSpecification specification) {
    if (answers.size() > 1) {
      return PrefillError.wrongNumberOfAnswers(specification.id().id(), 1, answers.size());
    }
    if (subAnswers.size() > 1) {
      return PrefillError.wrongNumberOfSubAnswers(specification.id().id(), 1, subAnswers.size());

    }
    if (!answers.isEmpty() && !subAnswers.isEmpty()) {
      return PrefillError.duplicateAnswer(specification.id().id());
    }

    return null;
  }

  public static PrefillError validateMinimumNumberOfDelsvar(List<Svar> answers, int minimum,
      ElementSpecification specification) {
    final var hasTooFewDelsvar = answers.stream()
        .anyMatch(answer -> answer.getDelsvar().size() < minimum);

    if (hasTooFewDelsvar) {
      final var numberOfSubAnswers = answers.stream()
          .map(Svar::getDelsvar)
          .filter(delsvar -> delsvar.size() < minimum)
          .findFirst()
          .stream()
          .mapToInt(List::size)
          .sum();

      return PrefillError.wrongNumberOfSubAnswers(
          specification.id().id(),
          minimum,
          numberOfSubAnswers
      );
    }
    return null;
  }
}