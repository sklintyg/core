package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Builder
public class HiddenElement {

  ElementId hiddenBy;
  ElementId id;
  Predicate<List<ElementData>> shouldHideByValue;
  @With
  ElementSimplifiedValue value;
}
