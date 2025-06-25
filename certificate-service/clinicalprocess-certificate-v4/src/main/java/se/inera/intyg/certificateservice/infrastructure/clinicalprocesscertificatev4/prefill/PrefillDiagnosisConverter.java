package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

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
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
@RequiredArgsConstructor
public class PrefillDiagnosisConverter implements PrefillConverter {

  private static final int LIMIT = 2;
  private static final String CV_TYPE_IDENTIFIER = "%s.2";
  private final DiagnosisCodeRepository diagnosisCodeRepository;

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDiagnosis.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {

    if (!(specification.configuration() instanceof ElementConfigurationDiagnosis configurationDiagnosis)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    final var answers = prefill.getSvar().stream()
        .filter(svar -> svar.getId().equals(specification.id().id()))
        .toList();

    final var prefillError = PrefillValidator.validateNumberOfDelsvar(
        answers,
        LIMIT,
        specification
    );

    if (prefillError != null) {
      return PrefillAnswer.builder()
          .errors(List.of(prefillError))
          .build();
    }

    if (answers.isEmpty()) {
      return null;
    }

    try {
      final var cvTypes = answers.stream()
          .map(Svar::getDelsvar)
          .flatMap(List::stream)
          .filter(subAnswer -> subAnswer.getId()
              .equals(CV_TYPE_IDENTIFIER.formatted(specification.id().id()))
          )
          .map(subAnswer -> PrefillUnmarshaller.cvType(subAnswer.getContent()))
          .map(Optional::orElseThrow)
          .toList();

      final var diagnosisCodeIsInvalid = cvTypes.stream()
          .map(CVType::getCode)
          .anyMatch(code -> diagnosisCodeRepository.findByCode(new DiagnosisCode(code)).isEmpty());

      if (diagnosisCodeIsInvalid) {
        return PrefillAnswer.builder()
            .errors(
                List.of(
                    PrefillError.invalidDiagnosisCode(
                        cvTypes.stream()
                            .map(CVType::getCode)
                            .filter(
                                code -> diagnosisCodeRepository.findByCode(new DiagnosisCode(code))
                                    .isEmpty())
                            .findFirst()
                            .orElseThrow()
                    )
                )
            )
            .build();
      }

      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueDiagnosisList.builder()
                          .diagnoses(
                              buildDiagnosis(answers, specification)
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
      ElementSpecification specification) {
    return null;
  }
}