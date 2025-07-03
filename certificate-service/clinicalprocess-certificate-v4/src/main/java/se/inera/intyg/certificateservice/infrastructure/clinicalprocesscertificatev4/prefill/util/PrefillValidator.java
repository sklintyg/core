package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util;

import java.util.ArrayList;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class PrefillValidator {

  private PrefillValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<PrefillError> validateSingleAnswerOrSubAnswer(List<Svar> answers,
      List<Delsvar> subAnswers,
      ElementSpecification specification) {
    final var prefillErrors = new ArrayList<PrefillError>();
    if (answers.size() > 1) {
      prefillErrors.add(
          PrefillError.wrongNumberOfAnswers(specification.id().id(), 1, answers.size())
      );
    }
    if (subAnswers.size() > 1) {
      prefillErrors.add(
          PrefillError.wrongNumberOfSubAnswers(specification.id().id(), 1, subAnswers.size())
      );
    }
    if (!answers.isEmpty() && !subAnswers.isEmpty()) {
      prefillErrors.add(PrefillError.duplicateAnswer(specification.id().id()));
    }

    return prefillErrors;
  }

  public static List<PrefillError> validateMinimumNumberOfDelsvar(List<Svar> answers, int minimum,
      ElementSpecification specification) {
    final var prefillErrors = new ArrayList<PrefillError>();
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

      prefillErrors.add(
          PrefillError.wrongNumberOfSubAnswers(
              specification.id().id(),
              minimum,
              numberOfSubAnswers
          )
      );
    }

    return prefillErrors;
  }

  public static List<PrefillError> validateDiagnosisCode(List<CVType> cvTypes,
      DiagnosisCodeRepository diagnosisCodeRepository) {
    final var prefillErrors = new ArrayList<PrefillError>();
    final var diagnosisCodeIsInvalid = cvTypes.stream()
        .map(CVType::getCode)
        .anyMatch(code -> diagnosisCodeRepository.findByCode(new DiagnosisCode(code)).isEmpty());

    if (diagnosisCodeIsInvalid) {
      prefillErrors.add(
          PrefillError.invalidDiagnosisCode(getCode(cvTypes, diagnosisCodeRepository))
      );
    }
    return prefillErrors;
  }

  private static String getCode(List<CVType> cvTypes,
      DiagnosisCodeRepository diagnosisCodeRepository) {
    return cvTypes.stream()
        .map(CVType::getCode)
        .filter(
            code -> diagnosisCodeRepository.findByCode(new DiagnosisCode(code))
                .isEmpty()
        )
        .findFirst()
        .orElseThrow();
  }

  public static List<PrefillError> validateMinimumNumberOfDelsvar(Svar s, int minimumSubAnswers,
      ElementSpecification specification) {
    final var prefillErrors = new ArrayList<PrefillError>();
    if (s.getDelsvar().size() < minimumSubAnswers) {
      prefillErrors.add(PrefillError.wrongNumberOfSubAnswers(
              specification.id().id(),
              minimumSubAnswers,
              s.getDelsvar().size()
          )
      );
    }
    return prefillErrors;
  }

  public static List<PrefillError> validateDelsvarId(List<Delsvar> subAnswers,
      ElementConfiguration configuration, ElementSpecification specification) {
    final var prefillErrors = new ArrayList<PrefillError>();
    final var correctSubAnswer = subAnswers.stream()
        .anyMatch(
            subAnswer -> subAnswer.getId().equals(configuration.id().value())
        );

    if (!correctSubAnswer) {
      prefillErrors.add(
          PrefillError.invalidSubAnswerId(
              configuration.id().value(),
              subAnswers.stream()
                  .map(Delsvar::getId)
                  .filter(id -> !id.equals(configuration.id().value()))
                  .toList(),
              specification.id().id()
          )
      );
    }
    return prefillErrors;
  }
}