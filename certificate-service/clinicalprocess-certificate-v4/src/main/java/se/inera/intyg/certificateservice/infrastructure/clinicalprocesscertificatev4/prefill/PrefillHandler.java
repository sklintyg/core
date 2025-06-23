package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillHandler {

  private final Map<Class<? extends ElementConfiguration>, PrefillConverter> converters;

  public PrefillHandler(List<PrefillConverter> converters) {
    this.converters = converters.stream()
        .collect(Collectors.toMap(PrefillConverter::supports, Function.identity()));
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers,
      ElementSpecification elementSpecification) {
    final var converter = converters.get(elementSpecification.configuration().getClass());

    if (converter == null) {
      return PrefillAnswer.builder()
          .errors(List.of(
                  PrefillError.missingConverter(
                      elementSpecification.configuration().getClass().toString())
              )
          )
          .build();
    }

    return converter.prefillAnswer(answers, elementSpecification);
  }

  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification elementSpecification) {
    final var converter = converters.get(elementSpecification.configuration().getClass());

    if (converter == null) {
      return PrefillAnswer.builder()
          .errors(List.of(
                  PrefillError.missingConverter(
                      elementSpecification.configuration().getClass().toString())
              )
          )
          .build();
    }

    return converter.prefillSubAnswer(subAnswers, elementSpecification);
  }


  public Collection<PrefillAnswer> unknownAnswerIds(CertificateModel model,
      Forifyllnad answer) {
    return answer.getSvar().stream()
        .filter(s -> !model.elementSpecificationExists(new ElementId(s.getId())))
        .map(sp -> PrefillAnswer.answerNotFound(sp.getId()))
        .toList();
  }

  public Collection<PrefillAnswer> handlePrefill(CertificateModel model, Forifyllnad prefill) {
    return List.of();
  }
}
