package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class ElementConfigurationCheckboxMultipleCode implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
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

  public Code code(ElementValueCode elementValueCode) {
    return list.stream()
        .filter(code -> code.id().equals(elementValueCode.codeId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Cannot find matching code for dateId '%s'".formatted(elementValueCode.codeId())
            )
        )
        .code();
  }
}
