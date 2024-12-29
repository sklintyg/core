package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationCodeList implements ElementValidation {

  boolean mandatory;

  @Override
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId,
      List<ElementData> dataList) {
    validateElementData(data);
    final var codeList = getValue(data.value());

    if (!mandatory) {
      return Collections.emptyList();
    }

    return getMandatoryError(data, categoryId, codeList);
  }

  private List<ValidationError> getMandatoryError(ElementData data,
      Optional<ElementId> categoryId, ElementValueCodeList codeList) {

    if (codeList.isEmpty()) {
      return List.of(
          ValidationError.builder()
              .elementId(data.id())
              .fieldId(codeList.id())
              .categoryId(categoryId.orElse(null))
              .message(ErrorMessageFactory.missingMultipleOption())
              .build()
      );
    }
    return Collections.emptyList();
  }

  private static void validateElementData(ElementData data) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }
    if (data.value() == null) {
      throw new IllegalArgumentException("Element data value is null");
    }
  }

  private ElementValueCodeList getValue(ElementValue value) {
    if (value instanceof ElementValueCodeList codeList) {
      return codeList;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }
}