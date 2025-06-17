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
    return converter.prefillAnswer(answers, elementSpecification);
  }

  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification elementSpecification) {
    final var converter = converters.get(elementSpecification.configuration().getClass());
    //TODO: error handling for missing converter
    return converter.prefillSubAnswer(subAnswers, elementSpecification);
  }


  public Collection<PrefillAnswer> unknownAnswerIds(Svar answer,
      CertificateModel model) {
    final var converter = converters.get(
        model.elementSpecification(new ElementId(answer.getId())).configuration().getClass());
    return converter.unknownIds(answer, model);
  }
}
