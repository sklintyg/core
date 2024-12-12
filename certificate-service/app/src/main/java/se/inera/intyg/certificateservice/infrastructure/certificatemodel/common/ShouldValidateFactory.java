package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import java.util.List;
import java.util.function.Predicate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class ShouldValidateFactory {

  private ShouldValidateFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Predicate<List<ElementData>> radioBoolean(ElementId elementId) {
    return radioBoolean(elementId, true);
  }

  public static Predicate<List<ElementData>> radioBooleans(List<ElementId> elementIds) {
    return radioBooleans(elementIds, true);
  }

  public static Predicate<List<ElementData>> radioBoolean(ElementId elementId,
      boolean expectedValue) {
    return radioBooleans(List.of(elementId), expectedValue);
  }

  public static Predicate<List<ElementData>> radioBooleans(List<ElementId> elementIds,
      boolean expectedValue) {
    return elementData -> elementData.stream()
        .filter(data -> elementIds.contains(data.id()))
        .map(element -> (ElementValueBoolean) element.value())
        .anyMatch(
            value -> value != null && (
                expectedValue
                    ? value.value() != null && value.value() == expectedValue
                    : value.value() == null || value.value() == expectedValue
            )
        );
  }

  public static Predicate<List<ElementData>> codes(ElementId elementId, List<FieldId> fieldIds) {
    return elementData -> elementData.stream()
        .filter(data -> data.id().equals(elementId))
        .map(element -> (ElementValueCode) element.value())
        .anyMatch(value -> fieldIds.contains(value.codeId()));
  }

  public static Predicate<List<ElementData>> codeList(ElementId elementId, List<FieldId> fieldIds) {
    return elementData -> elementData.stream()
        .filter(data -> data.id().equals(elementId))
        .map(element -> (ElementValueCodeList) element.value())
        .map(ElementValueCodeList::list)
        .flatMap(List::stream)
        .anyMatch(value -> fieldIds.contains(value.codeId()));
  }

}