package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;

@Value
@Builder
public class ElementConfigurationRadioBoolean implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.RADIO_BOOLEAN;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  String selectedText;
  String unselectedText;

  @Override
  public ElementValue emptyValue() {
    return ElementValueBoolean.builder()
        .booleanId(id)
        .build();
  }

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueBoolean elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.of(ElementSimplifiedValueText.builder()
          .text("Ej angivet")
          .build());
    }

    return Optional.of(
        ElementSimplifiedValueText.builder()
            .text(Boolean.TRUE.equals(elementValue.value()) ? selectedText : unselectedText)
            .build()
    );
  }
}
