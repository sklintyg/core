package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueMedicalInvestigationList implements ElementValue {

  FieldId id;
  @With
  List<MedicalInvestigation> list;

}
