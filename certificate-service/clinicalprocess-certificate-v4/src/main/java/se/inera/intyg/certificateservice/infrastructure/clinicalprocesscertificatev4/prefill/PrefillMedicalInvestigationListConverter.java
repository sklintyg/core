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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class PrefillMedicalInvestigationListConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationMedicalInvestigationList.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationMedicalInvestigationList medicalInvestigationListConfig)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (subAnswers.size() != 3) {
      return PrefillAnswer.builder()
          .errors(List.of(
              PrefillError.wrongNumberOfSubAnswers(specification.id().id(), 1, subAnswers.size())))
          .build();
    }

    List<MedicalInvestigation> items;
    try {
      final var config = medicalInvestigationListConfig.list().getFirst();
      items = List.of(getMedicalInvestigation(subAnswers, config, specification.id()));
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueMedicalInvestigationList.builder()
                    .id(specification.configuration().id())
                    .list(items)
                    .build()
                ).build()
        ).build();
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationMedicalInvestigationList medicalInvestigationListConfig)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (answers.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(List.of(
              PrefillError.wrongNumberOfAnswers(specification.id().id(), 1, answers.size())))
          .build();
    }

    List<MedicalInvestigation> items;
    try {
      items = answers.stream()
          .map(answer -> {
            final var config = medicalInvestigationListConfig.list().get(answer.getInstans() - 1);
            return getMedicalInvestigation(answer.getDelsvar(), config, specification.id());
          })
          .toList();
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueMedicalInvestigationList.builder()
                    .id(specification.configuration().id())
                    .list(items)
                    .build()
                ).build()
        ).build();
  }

  private MedicalInvestigation getMedicalInvestigation(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    return MedicalInvestigation.builder()
        .investigationType(getCode(subAnswer, config, elementId))
        .date(getDate(subAnswer, config, elementId))
        .informationSource(getText(subAnswer, config, elementId))
        .build();
  }

  private ElementValueCode getCode(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    String code;
    final var cvType = PrefillUnmarshaller.cvType(
        getContentFromSubAnswer(subAnswer, elementId, "%s.1")
    );

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    code = cvType.get().getCode();

    return ElementValueCode.builder()
        .code(code)
        .codeId(config.investigationTypeId())
        .build();
  }

  private ElementValueDate getDate(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    final var date = LocalDate.parse((String) getContentFromSubAnswer(subAnswer, elementId, "%s.2")
        .getFirst());

    return ElementValueDate.builder()
        .date(date)
        .dateId(config.dateId())
        .build();
  }

  private ElementValueText getText(List<Delsvar> subAnswer,
      MedicalInvestigationConfig config, ElementId elementId) {
    final var text = (String) getContentFromSubAnswer(subAnswer, elementId, "%s.3")
        .getFirst();

    return ElementValueText.builder()
        .text(text)
        .textId(config.informationSourceId())
        .build();
  }

  private static List<Object> getContentFromSubAnswer(List<Delsvar> subAnswer,
      ElementId id, String idFormat) {
    return subAnswer.stream()
        .filter(answer -> answer.getId().equals(idFormat.formatted(id.id())))
        .findFirst()
        .orElseThrow().getContent();
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    return List.of();
  }

  public List<String> additionalIds(ElementId id) {
    return List.of(
        "%s.1".formatted(id.id()),
        "%s.2".formatted(id.id()),
        "%s.3".formatted(id.id())
    );
  }
}
