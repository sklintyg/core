package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
@RequiredArgsConstructor
public class PrefillDiagnosisConverter implements PrefillConverter {

  private static final int MINIMUM_SUB_ANSWERS = 2;
  private static final String CV_TYPE_IDENTIFIER = "%s.2";
  private static final String DESCRIPTION_IDENTIFIER = "%s.1";
  private final DiagnosisCodeRepository diagnosisCodeRepository;

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDiagnosis.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {

    if (!(specification.configuration() instanceof ElementConfigurationDiagnosis elementConfigurationDiagnosis)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    final var answers = prefill.getSvar().stream()
        .filter(svar -> svar.getId().equals(specification.id().id()))
        .toList();

    if (answers.isEmpty()) {
      return null;
    }

    try {
      final var subAnswerValidationError = PrefillValidator.validateMinimumNumberOfDelsvar(
          answers,
          MINIMUM_SUB_ANSWERS,
          specification
      );

      final var diagnosisCodeValidationError = PrefillValidator.validateDiagnosisCode(
          getCvTypes(specification, answers),
          diagnosisCodeRepository
      );

      if (validationFailed(subAnswerValidationError, diagnosisCodeValidationError)) {
        return PrefillAnswer.builder()
            .errors(getValidationErrors(subAnswerValidationError, diagnosisCodeValidationError))
            .build();
      }

      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueDiagnosisList.builder()
                          .diagnoses(
                              buildDiagnosis(
                                  answers,
                                  specification,
                                  elementConfigurationDiagnosis
                              )
                          )
                          .build()
                  )
                  .build()
          )
          .build();
    } catch (Exception ex) {
      return PrefillAnswer.invalidFormat(specification.id().id(), ex.getMessage());
    }
  }

  private List<ElementValueDiagnosis> buildDiagnosis(List<Svar> answers,
      ElementSpecification specification,
      ElementConfigurationDiagnosis elementConfigurationDiagnosis) {
    return answers.stream()
        .map(
            answer -> {
              final var instance = answer.getInstans() - 1;
              return toElementValueDiagnosis(
                  answer.getDelsvar(),
                  elementConfigurationDiagnosis,
                  instance,
                  specification
              );
            }
        )
        .toList();
  }

  private static List<CVType> getCvTypes(ElementSpecification specification, List<Svar> answers) {
    return answers.stream()
        .map(Svar::getDelsvar)
        .flatMap(List::stream)
        .filter(subAnswer -> subAnswer.getId()
            .equals(CV_TYPE_IDENTIFIER.formatted(specification.id().id()))
        )
        .map(subAnswer -> PrefillUnmarshaller.cvType(subAnswer.getContent()))
        .map(Optional::orElseThrow)
        .toList();
  }

  private ElementValueDiagnosis toElementValueDiagnosis(List<Delsvar> subAnswers,
      ElementConfigurationDiagnosis elementConfigurationDiagnosis, Integer instance,
      ElementSpecification specification) {
    final var subAnswerCode = getSubAnswerCode(subAnswers, specification);
    final var subAnswerDescription = getSubAnswerDescription(subAnswers, specification);

    return ElementValueDiagnosis.builder()
        .id(elementConfigurationDiagnosis.list().get(instance).id())
        .terminology(getTerminology(elementConfigurationDiagnosis, subAnswerCode))
        .description(subAnswerDescription)
        .code(subAnswerCode.getCode())
        .build();
  }

  private static CVType getSubAnswerCode(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    return subAnswers.stream()
        .filter(subAnswer -> subAnswer.getId()
            .equals(CV_TYPE_IDENTIFIER.formatted(specification.id().id()))
        )
        .map(subAnswer -> PrefillUnmarshaller.cvType(subAnswer.getContent()).orElseThrow())
        .findFirst()
        .orElseThrow();
  }

  private static String getSubAnswerDescription(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    return subAnswers.stream()
        .filter(subAnswer -> subAnswer.getId()
            .equals(DESCRIPTION_IDENTIFIER.formatted(specification.id().id()))
        )
        .map(subAnswer -> subAnswer.getContent().getFirst())
        .findFirst()
        .map(String.class::cast)
        .orElseThrow();
  }

  private static String getTerminology(ElementConfigurationDiagnosis elementConfigurationDiagnosis,
      CVType cvType) {
    return elementConfigurationDiagnosis.terminology().stream()
        .filter(elementDiagnosisTerminology -> elementDiagnosisTerminology.codeSystem()
            .equals(cvType.getCodeSystem()))
        .findFirst()
        .orElseThrow()
        .id();
  }

  private List<PrefillError> getValidationErrors(PrefillError subAnswerValidationError,
      PrefillError diagnosisCodeValidationError) {
    final var validationErrors = new ArrayList<PrefillError>();
    if (diagnosisCodeValidationError != null) {
      validationErrors.add(diagnosisCodeValidationError);
    }

    if (subAnswerValidationError != null) {
      validationErrors.add(subAnswerValidationError);
    }
    return validationErrors;
  }

  private static boolean validationFailed(PrefillError subAnswerValidationError,
      PrefillError diagnosisCodeValidationError) {
    return subAnswerValidationError != null || diagnosisCodeValidationError != null;
  }
}