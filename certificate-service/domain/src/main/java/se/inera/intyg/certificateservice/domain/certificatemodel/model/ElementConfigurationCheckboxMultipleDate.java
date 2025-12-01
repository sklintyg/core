package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class ElementConfigurationCheckboxMultipleDate implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CHECKBOX_MULTIPLE_DATE;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  List<CheckboxDate> dates;
  FieldId id;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDateList.builder()
        .dateListId(id)
        .dateList(Collections.emptyList())
        .build();
  }

  public Code code(ElementValueDate elementValueDate) {
    return dates.stream()
        .filter(checkboxDate -> checkboxDate.id().equals(elementValueDate.dateId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Cannot find matching code for dateId '%s'".formatted(elementValueDate.dateId())
            )
        )
        .code();
  }

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueDateList elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }
    if (elementValue.isEmpty()) {
      return Optional.of(ElementSimplifiedValueText.builder()
          .text("Ej angivet")
          .build());
    }

    final var matchedDates = elementValue.dateList().stream()
        .map(date -> createLabeledText(
            date.date() != null ? date.date().toString() : null,
            findLabelById(date.dateId())
        ))
        .toList();

    final var unmatchedDates = dates.stream()
        .filter(date -> elementValue.dateList().stream()
            .noneMatch(d -> date.id().value().equals(d.dateId().value()))
        )
        .map(checkboxDate -> createLabeledText("Ej angivet", checkboxDate.label()))
        .toList();

    return Optional.of(ElementSimplifiedValueLabeledList.builder()
        .list(Stream.concat(matchedDates.stream(), unmatchedDates.stream()).toList())
        .build());
  }

  private String findLabelById(FieldId dateId) {
    return dates.stream()
        .filter(config -> config.id().value().equals(dateId.value()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "No matching label found for id: " + dateId
        ))
        .label();
  }

  private ElementSimplifiedValueLabeledText createLabeledText(String text, String label) {
    return ElementSimplifiedValueLabeledText.builder()
        .label(label)
        .text(text)
        .build();
  }
}