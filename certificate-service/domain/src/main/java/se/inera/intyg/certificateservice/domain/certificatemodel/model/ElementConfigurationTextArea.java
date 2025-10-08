package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;

@Value
@Builder
public class ElementConfigurationTextArea implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.TEXT_AREA;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  String header;
  @Getter(onMethod = @__(@Override))
  String label;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;

  @Override
  public ElementValue emptyValue() {
    return ElementValueText.builder()
        .textId(id)
        .build();
  }

  @Override
  public Optional<ElementData> convert(ElementData elementData,
      ElementSpecification specification) {
    if (elementData.value() instanceof ElementValueText) {
      return Optional.of(elementData);
    }

    if (elementData.value() instanceof ElementValueIcf elementValueIcf
        && specification.configuration() instanceof ElementConfigurationIcf elementConfigurationIcf) {

      return Optional.of(
          elementData.withValue(
              ElementValueText.builder()
                  .textId(elementValueIcf.id())
                  .text(elementValueIcf.formatIcfValueText(elementConfigurationIcf))
                  .build()
          )
      );
    }

    return Optional.empty();
  }

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueText elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(ElementSimplifiedValueText.builder()
        .text(elementValue.text())
        .build());
  }
}