package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class PrefillDateRangeConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDateRange.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationDateRange)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (subAnswers.size() != 1) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, subAnswers.size())))
          .build();
    }

    LocalDate start;
    LocalDate end;
    try {
      final var datePeriod = PrefillUnmarshaller.datePeriodType(
          subAnswers.getFirst().getContent());

      if (datePeriod.isEmpty()) {
        return PrefillAnswer.invalidFormat();
      }

      start = PrefillUnmarshaller.toLocalDate(datePeriod.get().getStart());
      end = PrefillUnmarshaller.toLocalDate(datePeriod.get().getEnd());
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueDateRange.builder()
                    .id(specification.configuration().id())
                    .fromDate(start)
                    .toDate(end)
                    .build()
                ).build()
        ).build();
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationDateRange)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (answers.size() != 1) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, answers.size())))
          .build();
    }

    return prefillSubAnswer(answers.getFirst().getDelsvar(), specification);
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    return Collections.emptyList();
  }
}
