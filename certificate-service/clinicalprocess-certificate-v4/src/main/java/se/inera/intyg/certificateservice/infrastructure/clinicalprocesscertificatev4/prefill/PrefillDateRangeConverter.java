package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillDateRangeConverter implements PrefillConverter {

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

    final var prefillError = PrefillValidator.validateSingleAnswerOrSubAnswer(answers, subAnswers,
        specification);

    if (prefillError != null) {
      return PrefillAnswer.builder()
          .errors(List.of(prefillError))
          .build();
    }

    LocalDate start;
    LocalDate end;

    try {

      if (!answers.isEmpty()) {
        final var datePeriodAnswer = PrefillUnmarshaller.datePeriodType(
            answers.getFirst().getDelsvar().getFirst().getContent()
        );
        start = PrefillUnmarshaller.toLocalDate(datePeriodAnswer.get().getStart());
        end = PrefillUnmarshaller.toLocalDate(datePeriodAnswer.get().getEnd());
      } else if (!subAnswers.isEmpty()) {
        final var datePeriodSubAnswer = PrefillUnmarshaller.datePeriodType(
            subAnswers.getFirst().getContent());
        start = PrefillUnmarshaller.toLocalDate(datePeriodSubAnswer.get().getStart());
        end = PrefillUnmarshaller.toLocalDate(datePeriodSubAnswer.get().getEnd());
      } else {
        return null;
      }
    } catch (Exception e) {

      return PrefillAnswer.invalidFormat();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueDateRange.builder()
                    .id(configurationDateRange.id())
                    .fromDate(start)
                    .toDate(end)
                    .build()
                ).build()
        ).build();

  }
}
