package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

public class XmlGeneratorDate implements XmlGeneratorElementData {

  public List<Svar> generate(ElementData data) {
    if (!(data.value() instanceof ElementValueDate dateValue)) {
      return Collections.emptyList();
    }

    if (dateValue.date() == null) {
      return Collections.emptyList();
    }

    return XmlAnswerFactory.createAnswerFromString(
        data.id(),
        dateValue.dateId(),
        dateValue.date().toString()
    );
  }
}
