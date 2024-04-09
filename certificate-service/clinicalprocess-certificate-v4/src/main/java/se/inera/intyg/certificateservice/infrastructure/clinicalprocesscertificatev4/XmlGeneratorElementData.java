package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

public interface XmlGeneratorElementData {

  List<Svar> generate(ElementData data);

}
