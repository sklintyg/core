package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;

class ElementConfigurationRadioBooleanTest {

  @Test
  void shouldReturnSimplifiedValueForTrue() {
    final var value = ElementValueBoolean.builder()
        .value(true)
        .build();

    final var config = ElementConfigurationRadioBoolean.builder()
        .selectedText("Ja")
        .build();

    assertEquals(
        "Ja",
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );

  }

  @Test
  void shouldReturnSimplifiedValueForFalse() {
    final var value = ElementValueBoolean.builder()
        .value(false)
        .build();

    final var config = ElementConfigurationRadioBoolean.builder()
        .selectedText("Ja")
        .unselectedText("No")
        .build();

    assertEquals(
        "No",
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

  @Test
  void shouldReturnEmptyOptionalForNull() {
    final var config = ElementConfigurationRadioBoolean.builder()
        .selectedText("Ja")
        .unselectedText("No")
        .build();

    assertTrue(config.simplified(config.emptyValue()).isEmpty());
  }

}