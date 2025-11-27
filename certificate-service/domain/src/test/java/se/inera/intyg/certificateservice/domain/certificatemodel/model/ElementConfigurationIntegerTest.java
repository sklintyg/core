package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
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
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationInteger.builder()
        .build();

    assertEquals(expected, config.simplified(config.emptyValue()));
  }
}

