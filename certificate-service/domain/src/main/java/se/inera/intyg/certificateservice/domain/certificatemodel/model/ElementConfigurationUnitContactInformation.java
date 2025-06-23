package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;

@Value
@Builder
public class ElementConfigurationUnitContactInformation implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.ISSUING_UNIT;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;

  public static final ElementId UNIT_CONTACT_INFORMATION = new ElementId(
      "UNIT_CONTACT_INFORMATION");

  @Override
  public ElementValue emptyValue() {
    return ElementValueUnitContactInformation.builder().build();
  }

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueUnitContactInformation elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        ElementSimplifiedValueList.builder()
            .list(
                List.of(
                    elementValue.address(),
                    String.format("%s %s", elementValue.zipCode(), elementValue.city()),
                    elementValue.phoneNumber()
                )
            )
            .build()
    );
  }

  @Override
  public FieldId id() {
    return null;
  }
}
