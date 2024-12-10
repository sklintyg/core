package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;

class ElementConfigurationTextFieldTest {

  @Test
  void shouldReturnSimplifiedValue() {
    final var text = "Test text for this test";
    final var value = ElementValueText.builder()
        .text(text)
        .build();

    final var config = ElementConfigurationTextField.builder()
        .build();

    assertEquals(
        text,
        ((ElementSimplifiedValueText) config.simplified(value)).text()
    );
  }

}