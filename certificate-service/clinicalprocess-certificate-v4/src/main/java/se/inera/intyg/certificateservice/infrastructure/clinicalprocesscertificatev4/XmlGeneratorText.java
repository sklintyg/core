package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

public class XmlGeneratorText implements XmlGeneratorElementData {

  public List<Svar> generate(ElementData data) {
    if (!(data.value() instanceof ElementValueText textValue)) {
      return Collections.emptyList();
    }

    if (textValue.text() == null) {
      return Collections.emptyList();
    }

    return XmlAnswerFactory.createAnswerFromString(
        data.id(),
        textValue.textId(),
        textValue.text()
    );
  }
}
