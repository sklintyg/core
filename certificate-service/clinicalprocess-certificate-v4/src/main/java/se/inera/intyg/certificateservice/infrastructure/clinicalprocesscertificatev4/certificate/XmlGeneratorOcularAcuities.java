package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

@Component
public class XmlGeneratorOcularAcuities implements XmlGeneratorElementData {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueOcularAcuities.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueOcularAcuities ocularAcuities)) {
      return Collections.emptyList();
    }

    if (ocularAcuities.isEmpty()) {
      return Collections.emptyList();
    }

    return Collections.emptyList();
  }
}