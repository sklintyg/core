package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

public class XmlGeneratorValue {

  private final Map<Class<? extends ElementValue>, XmlGeneratorElementData> converters = Map.of(
      ElementValueDate.class, new XmlGeneratorDate(),
      ElementValueText.class, new XmlGeneratorText()
  );

  public List<Svar> generate(List<ElementData> elementData) {
    return elementData.stream()
        .filter(data -> !(data.value() instanceof ElementValueUnitContactInformation))
        .map(data -> {
          final var converter = converters.get(data.value().getClass());
          if (converter == null) {
            throw new IllegalStateException(
                "Converter for '%s' not found".formatted(data.value().getClass())
            );
          }
          return converter.generate(data);
        })
        .filter(Objects::nonNull)
        .toList();
  }
}
