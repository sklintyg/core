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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationCode implements ElementValidation {

  boolean mandatory;

  @Override
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId,
      List<ElementData> dataList) {
    validateElementData(data);
    final var code = getValue(data.value());
    if (mandatory && code.isEmpty()) {
      return List.of(
          ValidationError.builder()
              .elementId(data.id())
              .fieldId(code.codeId())
              .categoryId(categoryId.orElse(null))
              .message(ErrorMessageFactory.missingOption())
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


  private ElementValueCode getValue(ElementValue value) {
    if (value instanceof ElementValueCode code) {
      return code;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }
}