package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class ConfigurationConvertertMapper {

  private final Map<Class<? extends ElementConfiguration>, PrefillElementData> converters;

  public ConfigurationConvertertMapper(List<PrefillElementData> converters) {
    this.converters = converters.stream()
        .collect(Collectors.toMap(PrefillElementData::supports, Function.identity()));
  }

  public PrefillResult prefillAnswer(List<Svar> svar, ElementSpecification elementSpecification) {
    final var converter = converters.get(elementSpecification.configuration().getClass());
    return converter.prefillAnswer(svar, elementSpecification);
  }

  public PrefillResult prefillSubAnswer(List<Delsvar> delsvar,
      ElementSpecification elementSpecification) {
    final var converter = converters.get(elementSpecification.configuration().getClass());
    return converter.prefillSubAnswer(delsvar.getFirst(), elementSpecification);
  }

}
