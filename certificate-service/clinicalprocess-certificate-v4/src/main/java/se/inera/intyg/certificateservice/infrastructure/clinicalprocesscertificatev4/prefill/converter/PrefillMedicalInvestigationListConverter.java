package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillMedicalInvestigationListConverter implements PrefillStandardConverter {

  private static final int MINIMUM_SUB_ANSWERS = 3;

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

    final var prefillError = PrefillValidator.validateMinimumNumberOfDelsvar(
        answers,
        MINIMUM_SUB_ANSWERS,
        specification
    );

    if (!prefillError.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(prefillError)
          .build();
    }

    final var prefillErrors = new ArrayList<PrefillError>();

    final var elementData = ElementData.builder()
        .id(specification.id())
        .value(
            ElementValueMedicalInvestigationList.builder()
                .id(specification.configuration().id())
                .list(
                    answers.stream()
                        .map(answer -> {
                          try {
                            final var subAnswers = answer.getDelsvar();
                            final var instance = answer.getInstans() - 1;
                            final var config = medicalInvestigationListConfig.list()
                                .get(instance);
                            return MedicalInvestigation.builder()
                                .id(config.id())
                                .investigationType(
                                    getCode(subAnswers, config, specification.id())
                                )
                                .date(getDate(subAnswers, config, specification.id())
                                )
                                .informationSource(
                                    getText(subAnswers, config, specification.id())
                                )
                                .build();
                          } catch (Exception ex) {
                            prefillErrors.add(
                                PrefillError.invalidFormat(answer.getId(), ex.getMessage()));
                            return null;
                          }
                        })
                        .filter(Objects::nonNull)
                        .toList()
                )
                .build()
        )
        .build();

    return PrefillAnswer.builder()
        .elementData(elementData)
        .errors(prefillErrors)
        .build();
  }

  private ElementValueCode getCode(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    final var cvType = PrefillUnmarshaller.cvType(
        getContentFromSubAnswer(subAnswer, elementId, "%s.1")
    );

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    final var code = new Code(cvType.get().getCode(), cvType.get().getCodeSystem(),
        cvType.get().getDisplayName());

    final var validCode = config.typeOptions().stream()
        .filter(c -> c.matches(code))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(
                "Code not found: '%s'".formatted(code))
        );

    return ElementValueCode.builder()
        .codeId(config.investigationTypeId())
        .code(validCode.code())
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