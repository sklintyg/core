package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class ElementConfigurationCheckboxDateRangeListTest {

  private static final String DATE_RANGE_ONE = "DATE_RANGE_ONE";
  private static final String DATE_RANGE_LABEL_ONE = "DATE_RANGE_LABEL_ONE";
  private static final String DATE_RANGE_TWO = "DATE_RANGE_TWO";
  private static final String DATE_RANGE_LABEL_TWO = "DATE_RANGE_LABEL_TWO";

  @Test
  void shallReturnDateRangeIfExists() {
    final var expectedDateRange = new ElementConfigurationCode(
        new FieldId(DATE_RANGE_TWO), DATE_RANGE_LABEL_TWO,
        new Code("CODE", "CODESYSTEM", "DISPLAY_NAME")
    );

    final var checkboxDateRangeList = ElementConfigurationCheckboxDateRangeList.builder()
        .dateRanges(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(DATE_RANGE_ONE), DATE_RANGE_LABEL_ONE,
                    new Code("CODE", "CODESYSTEM", "DISPLAY_NAME")
                ),
                expectedDateRange
            )
        )
        .build();

    assertEquals(expectedDateRange,
        checkboxDateRangeList.checkboxDateRange(new FieldId(DATE_RANGE_TWO)).orElseThrow()
    );
  }

  @Test
  void shallReturnEmptyIfRangeNotExists() {
    final var checkboxDateRangeList = ElementConfigurationCheckboxDateRangeList.builder()
        .dateRanges(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(DATE_RANGE_ONE), DATE_RANGE_LABEL_ONE,
                    new Code("CODE", "CODESYSTEM", "DISPLAY_NAME")
                )
            )
        )
        .build();

    assertTrue(checkboxDateRangeList.checkboxDateRange(new FieldId(DATE_RANGE_TWO)).isEmpty());
  }

  @Test
  void shallReturnEmptyIfRangesAreNull() {
    final var checkboxDateRangeList = ElementConfigurationCheckboxDateRangeList.builder()
        .build();

    assertTrue(checkboxDateRangeList.checkboxDateRange(new FieldId(DATE_RANGE_TWO)).isEmpty());
  }

  @Test
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationCheckboxDateRangeList.builder().build();
    final var value = ElementValueDateRangeList.builder().dateRangeList(List.of()).build();
    final var simplified = config.simplified(value);

    assertEquals(expected, simplified);
  }

  @Test
  void shouldReturnSimplifiedValue() {
    final var from = LocalDate.now();
    final var to = LocalDate.now().plusDays(7);
    final var config = ElementConfigurationCheckboxDateRangeList.builder()
        .dateRanges(List.of(
            new ElementConfigurationCode(new FieldId("id1"), "Label 1",
                new Code("code1", "system", "display1")),
            new ElementConfigurationCode(new FieldId("id2"), "Label 2",
                new Code("code2", "system", "display2"))
        ))
        .build();

    final var value = ElementValueDateRangeList.builder()
        .dateRangeList(List.of(
            DateRange.builder().dateRangeId(new FieldId("id1")).from(from).to(to).build(),
            DateRange.builder().dateRangeId(new FieldId("id2")).from(from).to(to).build()
        ))
        .build();

    final var result = config.simplified(value);

    final var expected = ElementSimplifiedValueTable.builder()
        .headings(List.of("Nedsättningsgrad", "Från och med", "Till och med"))
        .values(List.of(
            List.of("display1", from.toString(), to.toString()),
            List.of("display2", from.toString(), to.toString())
        ))
        .build();

    assertEquals(expected, result.get());
  }
}