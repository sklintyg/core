package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
@RequiredArgsConstructor
public class PrefillCustomDiagnosisListConverter implements PrefillCustomConverter {

  private static final int MINIMUM_SUB_ANSWERS = 1;
  private final DiagnosisCodeRepository diagnosisCodeRepository;

  @Override
  public CustomMapperId id() {
    return CustomMapperId.UNIFIED_DIAGNOSIS_LIST;
  }

  @Override
  public PrefillAnswer prefillAnswer(final ElementSpecification specification,
      final Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationDiagnosis elementConfigurationDiagnosis)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    final var answerOptional = prefill.getSvar().stream()
        .filter(svar -> svar.getId().equals(specification.id().id()))
        .findFirst();

    if (answerOptional.isEmpty()) {
      return null;
    }

    final var answer = answerOptional.get();
    final var prefillErrors = new ArrayList<>(
        PrefillValidator.validateMinimumNumberOfDelsvar(
            answer,
            MINIMUM_SUB_ANSWERS,
            specification
        ));

    final var delsvarList = answer.getDelsvar();
    final var diagnoses = getDiagnosisIndexStream(delsvarList, answer.getId())
        .mapToObj(diagnosisIndex -> {
          try {
            return extractAndValidateDiagnosis(
                delsvarList,
                diagnosisIndex,
                elementConfigurationDiagnosis,
                specification,
                prefillErrors
            );
          } catch (Exception e) {
            prefillErrors.add(PrefillError.invalidFormat(specification.id().id(), e.getMessage()));
            return null;
          }
        })
        .filter(Objects::nonNull)
        .toList();

    final var data = ElementData.builder()
        .id(specification.id())
        .value(ElementValueDiagnosisList.builder().diagnoses(diagnoses).build())
        .build();

    return PrefillAnswer.builder()
        .elementData(data)
        .errors(prefillErrors)
        .build();
  }

  private ElementValueDiagnosis extractAndValidateDiagnosis(
      final List<Delsvar> delsvarList,
      final int diagnosisIndex,
      final ElementConfigurationDiagnosis elementConfigurationDiagnosis,
      final ElementSpecification specification,
      final List<PrefillError> prefillErrors
  ) {
    final var descriptionDelsvar = getDescriptionDelsvar(specification, delsvarList,
        diagnosisIndex);
    final var codeDelsvar = getCodeDelsvar(specification, delsvarList, diagnosisIndex);

    final var optionalCvType = PrefillUnmarshaller.cvType(codeDelsvar.getContent());
    final var validationErrors = new ArrayList<>(
        PrefillValidator.validateDiagnosisCode(
            optionalCvType.map(List::of).orElse(Collections.emptyList()),
            diagnosisCodeRepository
        ));

    if (!validationErrors.isEmpty()) {
      prefillErrors.addAll(validationErrors);
      return null;
    }

    try {
      final var cvType = optionalCvType.orElseThrow();
      final var code = cvType.getCode();
      final var terminology = getTerminology(elementConfigurationDiagnosis, cvType);
      final var diagnosisId = getDiagnosisId(diagnosisIndex, elementConfigurationDiagnosis);
      final var description = getDescription(descriptionDelsvar, code);
      return ElementValueDiagnosis.builder()
          .id(diagnosisId)
          .terminology(terminology)
          .description(description)
          .code(code)
          .build();
    } catch (Exception e) {
      prefillErrors.add(PrefillError.invalidFormat(specification.id().id(), e.getMessage()));
      return null;
    }
  }

  private static FieldId getDiagnosisId(int diagnosisIndex,
      ElementConfigurationDiagnosis elementConfigurationDiagnosis) {
    if (elementConfigurationDiagnosis.list().size() <= diagnosisIndex) {
      throw new IllegalStateException("Too many diagnoses in prefill answer");
    }
    return elementConfigurationDiagnosis.list().get(diagnosisIndex).id();
  }

  private String getDescription(Optional<Delsvar> descriptionDelsvar, String code) {
    return descriptionDelsvar.isEmpty() || descriptionDelsvar.get().getContent().isEmpty()
        ? diagnosisCodeRepository.getByCode(new DiagnosisCode(code)).description().description()
        : (String) descriptionDelsvar.get().getContent().getFirst();
  }

  private static String getTerminology(ElementConfigurationDiagnosis elementConfigurationDiagnosis,
      CVType cvType) {
    return elementConfigurationDiagnosis.terminology().stream()
        .filter(terminology -> terminology.isValidCodeSystem(cvType.getCodeSystem()))
        .findFirst()
        .orElseThrow()
        .id();
  }

  private Optional<Delsvar> getDelsvarByOffset(final ElementSpecification elementSpecification,
      final List<Delsvar> delsvarList,
      final int diagnosisIndex,
      final int offset) {
    return delsvarList.stream()
        .filter(delsvar -> delsvar.getId()
            .equals(String.format("%s.%s", elementSpecification.id().id(),
                offset + diagnosisIndex * 2)))
        .findFirst();
  }

  private Optional<Delsvar> getDescriptionDelsvar(final ElementSpecification elementSpecification,
      final List<Delsvar> delsvarList,
      final int diagnosisIndex) {
    return getDelsvarByOffset(elementSpecification, delsvarList, diagnosisIndex, 1);
  }

  private Delsvar getCodeDelsvar(final ElementSpecification elementSpecification,
      final List<Delsvar> delsvarList,
      final int diagnosisIndex) {
    return getDelsvarByOffset(elementSpecification, delsvarList, diagnosisIndex, 2).orElseThrow();
  }

  private static IntStream getDiagnosisIndexStream(List<Delsvar> delsvarList, String answerId) {
    final var diagnosisCount = (int) delsvarList.stream()
        .filter(delsvar -> delsvar.getId()
            .matches(String.format("%s\\.(\\d*[02468])$", answerId)))
        .count();
    return IntStream.range(0, diagnosisCount);

  }
}
