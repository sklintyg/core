package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
@RequiredArgsConstructor
public class PrefillDiagnosisConverter implements PrefillStandardConverter {

  private static final int MINIMUM_SUB_ANSWERS = 1;
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

    final var prefillErrors = new ArrayList<PrefillError>();
    final var data = ElementData.builder()
        .id(specification.id())
        .value(
            ElementValueDiagnosisList.builder()
                .diagnoses(
                    answers.stream()
                        .map(answer -> {
                          try {

                            final var validationErrors = new ArrayList<PrefillError>();
                            validationErrors.addAll(
                                PrefillValidator.validateMinimumNumberOfDelsvar(
                                    answer,
                                    MINIMUM_SUB_ANSWERS,
                                    specification)
                            );

                            validationErrors.addAll(
                                PrefillValidator.validateDiagnosisCode(
                                    getCvTypes(specification, answer),
                                    diagnosisCodeRepository
                                )
                            );

                            if (!validationErrors.isEmpty()) {
                              prefillErrors.addAll(validationErrors);
                              return null;
                            }

                            final var instance = answer.getInstans() - 1;
                            return toElementValueDiagnosis(
                                answer.getDelsvar(),
                                elementConfigurationDiagnosis,
                                instance,
                                specification
                            );
                          } catch (Exception e) {
                            prefillErrors.add(
                                PrefillError.invalidFormat(answer.getId(), e.getMessage()));
                            return null;
                          }
                        })
                        .filter(Objects::nonNull)
                        .toList())
                .build())
        .build();

    return PrefillAnswer.builder()
        .elementData(data)
        .errors(prefillErrors)
        .build();
  }

  private List<CVType> getCvTypes(ElementSpecification specification, Svar answer) {
    return answer.getDelsvar().stream()
        .filter(subAnswer -> subAnswer.getId()
            .equals(CV_TYPE_IDENTIFIER.formatted(specification.id().id())))
        .map(subAnswer -> PrefillUnmarshaller.cvType(subAnswer.getContent()))
        .map(Optional::orElseThrow)
        .toList();
  }

  private ElementValueDiagnosis toElementValueDiagnosis(List<Delsvar> subAnswers,
      ElementConfigurationDiagnosis elementConfigurationDiagnosis, Integer instance,
      ElementSpecification specification) {
    final var subAnswerCode = getSubAnswerCode(subAnswers, specification);
    final var subAnswerDescription = getSubAnswerDescription(subAnswers, specification,
        subAnswerCode.getCode());

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

  /**
   * Retrieves the description from the subAnswers list. If the description is not present in the
   * Delsvar, it falls back to using the description associated with the diagnosis code from the
   * repository.
   *
   * @param subAnswers    List of Delsvar objects to search for the description.
   * @param specification The element specification.
   * @param code          The diagnosis code used to fetch the description if missing.
   * @return The description string.
   */
  private String getSubAnswerDescription(List<Delsvar> subAnswers,
      ElementSpecification specification, String code) {
    return subAnswers.stream()
        .filter(subAnswer -> subAnswer.getId()
            .equals(DESCRIPTION_IDENTIFIER.formatted(specification.id().id()))
        )
        .map(subAnswer -> subAnswer.getContent().getFirst())
        .findFirst()
        .map(String.class::cast)
        .orElseGet(() -> diagnosisCodeRepository.getByCode(new DiagnosisCode(code)).description()
            .description());
  }

  private static String getTerminology(ElementConfigurationDiagnosis elementConfigurationDiagnosis,
      CVType cvType) {
    return elementConfigurationDiagnosis.terminology().stream()
        .filter(elementDiagnosisTerminology -> elementDiagnosisTerminology.isValidCodeSystem(
            cvType.getCodeSystem()))
        .findFirst()
        .orElseThrow()
        .id();
  }
}