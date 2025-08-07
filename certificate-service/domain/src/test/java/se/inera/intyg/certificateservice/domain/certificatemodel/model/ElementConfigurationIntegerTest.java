package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;

class ElementConfigurationIntegerTest {

  @Test
  void shouldReturnSimplifiedValue() {
    final var value = ElementValueInteger.builder()
        .value(42)
        .build();

    final var config = ElementConfigurationInteger.builder()
        .build();

    assertEquals(
        "42",
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

  @Test
  void shouldReturnEmptyOptionalForNull() {
    final var config = ElementConfigurationInteger.builder()
        .build();

    assertTrue(config.simplified(config.emptyValue()).isEmpty());
  }
}

