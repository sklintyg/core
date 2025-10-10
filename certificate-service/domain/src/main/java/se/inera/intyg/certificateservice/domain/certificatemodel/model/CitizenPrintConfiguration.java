package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;

@Value
@Builder
public class CitizenPrintConfiguration {

  ElementId hiddenBy;
  Predicate<List<ElementData>> shouldHide;
  ElementSimplifiedValue replacementValue;
}
