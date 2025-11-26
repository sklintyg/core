package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;

class ElementConfigurationDateRangeTest {

  private static final String DATE_RANGE_NAME = "Date range name";
  private static final String LABEL_TO = "To label";
  private static final String LABEL_FROM = "From label";
  public static final String DATE_RANGE_ID = "dateRangeId";

  @Test
  void shouldReturnDateRangeIfExists() {
    final var value = ElementConfigurationDateRange.builder()
        .id(new FieldId(DATE_RANGE_ID))
        .name(DATE_RANGE_NAME)
        .labelTo(LABEL_TO)
        .labelFrom(LABEL_FROM)
        .build();

    final var expected = ElementValueDateRange.builder()
        .id(new FieldId(DATE_RANGE_ID))
        .build();

    assertEquals(expected, value.emptyValue());

  }

  @Test
  void shouldReturnSimplifiedValueIfValueIsEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );

    final var config = ElementConfigurationDateRange.builder().build();
    final var value = ElementValueDateRange.builder().build();
    final var simplified = config.simplified(value);
    assertEquals(expected, simplified);
  }

  @Test
  void shouldReturnSimplifiedValue() {
    final var config = ElementConfigurationDateRange.builder()
        .name(DATE_RANGE_NAME)
        .labelTo(LABEL_TO)
        .labelFrom(LABEL_FROM)
        .build();

    final var value = ElementValueDateRange.builder()
        .fromDate(LocalDate.parse("2023-01-01"))
        .toDate(LocalDate.parse("2023-12-31"))
        .build();

    final var result = config.simplified(value);

    final var expected = ElementSimplifiedValueTable.builder()
        .headings(List.of(DATE_RANGE_NAME, LABEL_FROM, LABEL_TO))
        .values(List.of(
            List.of("2023-01-01", "2023-12-31")))
        .build();

    assertEquals(expected, result.get());
  }

}