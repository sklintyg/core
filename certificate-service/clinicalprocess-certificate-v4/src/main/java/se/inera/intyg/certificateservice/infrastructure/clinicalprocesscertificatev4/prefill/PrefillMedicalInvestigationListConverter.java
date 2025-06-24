package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillMedicalInvestigationListConverter implements PrefillConverter {

  private static final int LIMIT = 3;

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationMedicalInvestigationList.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationMedicalInvestigationList medicalInvestigationListConfig)) {
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

    try {
      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueMedicalInvestigationList.builder()
                          .id(specification.configuration().id())
                          .list(
                              getMedicalInvestigations(
                                  answers,
                                  medicalInvestigationListConfig.list(),
                                  specification.id()
                              )
                          )
                          .build()
                  ).build()
          )
          .build();
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }
  }

  private List<MedicalInvestigation> getMedicalInvestigations(List<Svar> answers,
      List<MedicalInvestigationConfig> medicalInvestigationConfigs, ElementId elementId) {
    return answers.stream()
        .map(answer -> {
              final var subAnswers = answer.getDelsvar();
              final var instance = answer.getInstans() - 1;
              final var config = medicalInvestigationConfigs.get(instance);
              return MedicalInvestigation.builder()
                  .investigationType(getCode(subAnswers, config, elementId))
                  .date(getDate(subAnswers, config, elementId))
                  .informationSource(getText(subAnswers, config, elementId))
                  .build();
            }
        )
        .toList();
  }

  private ElementValueCode getCode(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    final var cvType = PrefillUnmarshaller.cvType(
        getContentFromSubAnswer(subAnswer, elementId, "%s.1")
    );

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    return ElementValueCode.builder()
        .codeId(config.investigationTypeId())
        .code(cvType.get().getCode())
        .build();
  }

  private ElementValueDate getDate(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    final var date = LocalDate.parse((String) getContentFromSubAnswer(subAnswer, elementId, "%s.2")
        .getFirst());

    return ElementValueDate.builder()
        .dateId(config.dateId())
        .date(date)
        .build();
  }

  private ElementValueText getText(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    final var text = (String) getContentFromSubAnswer(subAnswer, elementId, "%s.3")
        .getFirst();

    return ElementValueText.builder()
        .textId(config.informationSourceId())
        .text(text)
        .build();
  }

  private static List<Object> getContentFromSubAnswer(List<Delsvar> subAnswer,
      ElementId id, String idFormat) {
    return subAnswer.stream()
        .filter(answer -> answer.getId().equals(idFormat.formatted(id.id())))
        .findFirst()
        .orElseThrow()
        .getContent();
  }
}