package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

public class XmlGeneratorDate implements XmlGeneratorElementData {

  public Svar generate(ElementData data) {
    if (!(data.value() instanceof ElementValueDate dateValue)) {
      return null;
    }

    if (dateValue.date() == null) {
      return null;
    }

    return XmlAnswerFactory.createAnswerFromString(
        data.id(),
        dateValue.dateId(),
        dateValue.date().toString()
    );
  }
}
