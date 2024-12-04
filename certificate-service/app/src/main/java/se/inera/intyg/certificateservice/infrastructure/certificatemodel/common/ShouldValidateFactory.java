package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import java.util.List;
import java.util.function.Predicate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class ShouldValidateFactory {

  private ShouldValidateFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Predicate<List<ElementData>> radioBoolean(ElementId elementId) {
    return radioBoolean(elementId, true);
  }

  public static Predicate<List<ElementData>> radioBoolean(ElementId elementId,
      boolean expectedValue) {
    return elementData -> elementData.stream()
        .filter(data -> data.id().equals(elementId))
        .map(element -> (ElementValueBoolean) element.value())
        .anyMatch(
            value -> value != null && value.value() != null && value.value() == expectedValue);
  }

}
