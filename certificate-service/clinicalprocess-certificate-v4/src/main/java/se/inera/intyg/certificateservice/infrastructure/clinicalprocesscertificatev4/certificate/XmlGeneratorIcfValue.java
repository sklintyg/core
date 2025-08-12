package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

@Component
public class XmlGeneratorIcfValue implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueIcf.class;
  }

  @Override
  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueIcf icfValue)) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationIcf icfConfiguration)) {
      return Collections.emptyList();
    }

    if (icfValue.text() == null || icfValue.text().isEmpty()) {
      return Collections.emptyList();
    }

    return XmlAnswerFactory.createAnswerFromString(
        data.id(),
        icfValue.id(),
        """
            %s %s
            
            %s
            """.formatted(
            icfConfiguration.collectionsLabel(),
            String.join(" - ", icfValue.icfCodes()),
            icfValue.text()
        ));
  }
}