package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

class ElementConfigurationDateTest {

  @Test
  void shouldReturnSimplifiedValue() {
    final var date = LocalDate.now();
    final var value = ElementValueDate.builder()
        .date(date)
        .build();

    final var config = ElementConfigurationDate.builder()
        .build();

    assertEquals(
        date.format(DateTimeFormatter.ISO_DATE),
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }


  @Test
  void shouldReturnEmptyOptionalForNull() {
    final var config = ElementConfigurationDate.builder()
        .build();

    assertTrue(config.simplified(config.emptyValue()).isEmpty());
  }

}