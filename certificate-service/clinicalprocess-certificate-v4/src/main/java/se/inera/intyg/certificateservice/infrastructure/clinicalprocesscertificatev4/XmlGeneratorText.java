package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

public class XmlGeneratorText implements XmlGeneratorElementData {

  public Svar generate(ElementData data) {
    if (!(data.value() instanceof ElementValueText textValue)) {
      return null;
    }

    if (textValue.text() == null) {
      return null;
    }

    return XmlAnswerFactory.createAnswerFromString(
        data.id(),
        textValue.textId(),
        textValue.text()
    );
  }

}
