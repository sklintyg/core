package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.SubAnswersUtil;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillDateRangeConverter implements PrefillStandardConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDateRange.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationDateRange configurationDateRange)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    final var answers = prefill.getSvar().stream()
        .filter(svar -> svar.getId().equals(specification.id().id()))
        .toList();

    final var subAnswers = prefill.getSvar().stream()
        .map(Svar::getDelsvar)
        .flatMap(List::stream)
        .filter(delsvar -> delsvar.getId().equals(specification.id().id()))
        .toList();

    if (answers.isEmpty() && subAnswers.isEmpty()) {
      return null;
    }

    final var prefillErrors = new ArrayList<PrefillError>();
    prefillErrors.addAll(
        PrefillValidator.validateSingleAnswerOrSubAnswer(
            answers,
            subAnswers,
            specification
        )
    );

    prefillErrors.addAll(
        PrefillValidator.validateDelsvarId(
            SubAnswersUtil.get(answers, subAnswers),
            configurationDateRange,
            specification
        )
    );

    if (!prefillErrors.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(prefillErrors)
          .build();
    }

    try {
      final var content = getContent(subAnswers, answers, configurationDateRange);
      final var datePeriodAnswer = PrefillUnmarshaller.datePeriodType(
          content
      );

      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueDateRange.builder()
                          .id(configurationDateRange.id())
                          .fromDate(PrefillUnmarshaller.toLocalDate(
                              datePeriodAnswer.orElseThrow().getStart())
                          )
                          .toDate(
                              PrefillUnmarshaller.toLocalDate(
                                  datePeriodAnswer.orElseThrow().getEnd())
                          )
                          .build()
                  ).build()
          )
          .build();
    } catch (Exception ex) {
      return PrefillAnswer.invalidFormat(specification.id().id(), ex.getMessage());
    }
  }

  private static List<Object> getContent(List<Delsvar> subAnswers, List<Svar> answers,
      ElementConfigurationDateRange configurationDateRange) {
    if (!subAnswers.isEmpty()) {
      return subAnswers.stream()
          .filter(subAnswer -> subAnswer.getId().equals(configurationDateRange.id().value()))
          .toList().
          getFirst()
          .getContent();
    }
    return answers.getFirst()
        .getDelsvar()
        .stream()
        .filter(subAnswer -> subAnswer.getId().equals(configurationDateRange.id().value()))
        .toList()
        .getFirst()
        .getContent();
  }
}