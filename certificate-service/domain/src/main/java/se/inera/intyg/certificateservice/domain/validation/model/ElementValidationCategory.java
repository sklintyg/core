package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationCategory implements ElementValidation {

  boolean mandatory;
  List<ElementId> elements;


  @Override
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId,
      List<ElementData> dataList) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var elementsThatShouldBeValidated = dataList.stream()
        .filter(elementData -> elements.contains(elementData.id()))
        .toList();

    if (mandatory && validateElements(elementsThatShouldBeValidated)) {
      return List.of(
          ValidationError.builder()
              .elementId(new ElementId(data.id().id()))
              .categoryId(data.id())
              .fieldId(new FieldId(data.id().id()))
              .message(new ErrorMessage("Besvara minst en av frågorna."))
              .build()
      );
    }

    return List.of();
  }

  private boolean validateElements(List<ElementData> elementsThatShouldBeValidated) {
    final var invalidElements = elementsThatShouldBeValidated.stream()
        .map(ElementData::value)
        .filter(ElementValue::isEmpty)
        .toList();

    return invalidElements.size() == elementsThatShouldBeValidated.size();
  }
}