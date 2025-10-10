package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;

class ElementConfigurationCheckboxBooleanTest {

  @Test
  void shouldReturnSimplifiedValue() {
    final var value = ElementValueBoolean.builder()
        .value(true)
        .build();

    final var config = ElementConfigurationCheckboxBoolean.builder()
        .selectedText("Ja")
        .unselectedText("Ej angivet")
        .build();

    assertEquals(
        config.selectedText(),
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

  @Test
  void shouldReturnEmptyOptionalForNull() {
    final var text = "Ja";
    final var value = ElementValueBoolean.builder()
        .value(false)
        .build();

    final var config = ElementConfigurationCheckboxBoolean.builder()
        .selectedText(text)
        .unselectedText("Ej angivet")
        .build();

    assertEquals(
        config.unselectedText(),
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

}