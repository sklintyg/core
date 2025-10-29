package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;

class ElementConfigurationCheckboxBooleanTest {

  @Test
  void shouldReturnSimplifiedValue() {
    final var value = ElementValueBoolean.builder()
        .value(true)
        .build();

    final var config = ElementConfigurationCheckboxBoolean.builder()
        .label("Label 1")
        .selectedText("Ja")
        .unselectedText("Ej angivet")
        .build();

    assertAll(() -> assertEquals(config.selectedText(),
            ((ElementSimplifiedValueLabeledText) config.simplified(value).get()).text()),
        () -> assertEquals(config.label(),
            ((ElementSimplifiedValueLabeledText) config.simplified(value).get()).label()));
  }

  @Test
  void shouldReturnEmptyOptionalForNull() {
    final var text = "Ja";
    final var value = ElementValueBoolean.builder()
        .value(false)
        .build();

    final var config = ElementConfigurationCheckboxBoolean.builder()
        .label("Label 1")
        .selectedText(text)
        .unselectedText("Ej angivet")
        .build();

    assertAll(
        () -> assertEquals("Ej angivet",
            ((ElementSimplifiedValueLabeledText) config.simplified(value).get()).text()),
        () -> assertEquals(config.label(),
            ((ElementSimplifiedValueLabeledText) config.simplified(value).get()).label())
    );
  }

}