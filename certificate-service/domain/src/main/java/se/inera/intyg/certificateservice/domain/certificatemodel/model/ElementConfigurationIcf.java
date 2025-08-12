package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;

@Value
@Builder
public class ElementConfigurationIcf implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.ICF;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  String modalLabel;
  String collectionsLabel;
  String placeholder;
  IcfCodesPropertyType icfCodesPropertyName;

  @Override
  public ElementValue emptyValue() {
    return ElementValueIcf.builder()
        .build();
  }

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueIcf elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        ElementSimplifiedValueList.builder()
            .build());
  }
}