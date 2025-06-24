package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillDateConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDate.class;
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    return Collections.emptyList();
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationDate configurationDate)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    final var answer = prefill.getSvar().stream()
        .filter(svar -> svar.getId().equals(specification.id().id()))
        .toList();

    final var subAnswer = prefill.getSvar().stream()
        .map(Svar::getDelsvar)
        .flatMap(List::stream)
        .filter(delsvar -> delsvar.getId().equals(specification.id().id()))
        .toList();

    final var prefillError = PrefillValidator.validateSingleAnswerOrSubAnswer(answer, subAnswer,
        specification);

    if (prefillError != null) {
      return PrefillAnswer.builder()
          .errors(List.of(prefillError))
          .build();
    }

    final var noPrefill = answer.isEmpty() && subAnswer.isEmpty();

    return noPrefill
        ? null
        : PrefillAnswer.builder()
            .elementData(
                ElementData.builder()
                    .id(specification.id())
                    .value(
                        ElementValueDate.builder()
                            .dateId(configurationDate.id())
                            .date(handleContent(subAnswer, answer))
                            .build()
                    )
                    .build()
            )
            .build();
  }

  private static LocalDate handleContent(List<Delsvar> subAnswers, List<Svar> answers) {
    if (!subAnswers.isEmpty()) {
      return (LocalDate) subAnswers.getFirst().getContent().getFirst();
    }
    return (LocalDate) answers.stream()
        .map(Svar::getDelsvar)
        .toList()
        .getFirst()
        .getFirst()
        .getContent()
        .getFirst();
  }
}
