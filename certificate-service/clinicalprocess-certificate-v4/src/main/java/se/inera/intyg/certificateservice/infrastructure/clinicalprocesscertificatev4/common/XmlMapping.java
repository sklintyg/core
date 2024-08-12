package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

@Value
@Builder
public class XmlMapping {

  ElementMapping mapping;
  List<Svar> answers;
}
