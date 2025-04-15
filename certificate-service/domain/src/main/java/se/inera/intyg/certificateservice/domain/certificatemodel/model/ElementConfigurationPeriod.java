package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValuePeriod;

@Value
@Builder
public class ElementConfigurationPeriod implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.PERIOD;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  ElementDate fromDate;
  ElementDate toDate;

  @Override
  public ElementValue emptyValue() {
    return ElementValuePeriod.builder()
        .build();
  }

  @Override //TODO: hur vill vi ha simplified value?
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValuePeriod elementValuePeriod)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValuePeriod.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(ElementSimplifiedValueText.builder()
        .text(elementValuePeriod.fromDate().date().format(DateTimeFormatter.ISO_DATE) + " - " +
            elementValuePeriod.toDate().date().format(DateTimeFormatter.ISO_DATE))
        .build());
  }
}
