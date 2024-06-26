package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueDiagnosisList implements ElementValue {

  List<ElementValueDiagnosis> diagnosisList;

}
