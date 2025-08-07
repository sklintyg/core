package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueIcfValue implements ElementValue {

  FieldId id;
  String text;
  @With
  List<String> icfCodes;

  @Override
  public boolean isEmpty() {
    return icfCodes == null || icfCodes.isEmpty();
  }
}
