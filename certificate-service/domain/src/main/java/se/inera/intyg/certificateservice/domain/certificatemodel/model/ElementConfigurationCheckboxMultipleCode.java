package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class ElementConfigurationCheckboxMultipleCode implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod_ = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CHECKBOX_MULTIPLE_CODE;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  List<ElementConfigurationCode> list;
  ElementLayout elementLayout;

  @Override
  public ElementValue emptyValue() {
    return ElementValueCodeList.builder()
        .id(id)
        .list(Collections.emptyList())
        .build();
  }


  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueCodeList elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.of(ElementSimplifiedValueText.builder()
          .text("Ej angivet")
          .build());
    }

    return Optional.of(ElementSimplifiedValueList.builder()
        .list(elementValue.list().stream()
            .map(this::code)
            .map(Code::displayName)
            .toList())
        .build());
  }

  public Code code(ElementValueCode elementValueCode) {
    return list.stream()
        .filter(code -> code.id().equals(elementValueCode.codeId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Cannot find matching code for choice '%s'".formatted(elementValueCode.codeId())
            )
        )
        .code();
  }
}
