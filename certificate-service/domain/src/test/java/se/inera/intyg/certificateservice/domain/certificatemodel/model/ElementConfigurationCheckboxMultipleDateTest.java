package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class ElementConfigurationCheckboxMultipleDateTest {

  private static final String FIELD_ID = "FIELD_ID";
  private static final String DATE_FIELD_ID = "DATE_FIELD_ID";
  private static final String DATE_FIELD_ID_TWO = "DATE_FIELD_ID_TWO";
  private static final String DATE_FIELD_ID_THREE = "DATE_FIELD_ID_THREE";
  private static final String LABEL = "LABEL";
  private static final String LABEL_TWO = "LABEL_TWO";
  private static final String LABEL_THREE = "LABEL_THREE";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String CODE = "CODE";
  private static final String DISPLAY_NAME = "DISPLAY_NAME";
  private static final String CODE_TWO = "CODE_TWO";
  private static final String CODE_THREE = "CODE_THREE";
  private static final String DISPLAY_NAME_TWO = "DISPLAY_NAME_TWO";
  private static final String DISPLAY_NAME_THREE = "DISPLAY_NAME_THREE";

  @Test
  void shallReturnEmptyValue() {
    final var emptyValue = ElementValueDateList.builder()
        .dateListId(new FieldId(FIELD_ID))
        .dateList(Collections.emptyList())
        .build();

    final var configuration = ElementConfigurationCheckboxMultipleDate.builder()
        .id(new FieldId(FIELD_ID))
        .build();

    assertEquals(emptyValue, configuration.emptyValue());
  }

  @Test
  void shallReturnMatchingCode() {
    final var expectedCode = new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO);

    final var configuration = ElementConfigurationCheckboxMultipleDate.builder()
        .id(new FieldId(FIELD_ID))
        .dates(
            List.of(
                CheckboxDate.builder()
                    .id(new FieldId(DATE_FIELD_ID))
                    .label(LABEL)
                    .code(new Code(CODE, CODE_SYSTEM, DISPLAY_NAME))
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId(DATE_FIELD_ID_TWO))
                    .label(LABEL_TWO)
                    .code(new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO))
                    .build()
            )
        )
        .build();

    final var dateValue = ElementValueDate.builder()
        .dateId(new FieldId(DATE_FIELD_ID_TWO))
        .build();

    assertEquals(expectedCode, configuration.code(dateValue));
  }

  @Test
  void shallThrowIfNoMatchingCode() {
    final var configuration = ElementConfigurationCheckboxMultipleDate.builder()
        .id(new FieldId(FIELD_ID))
        .dates(Collections.emptyList())
        .build();

    final var dateValue = ElementValueDate.builder()
        .dateId(new FieldId(DATE_FIELD_ID_TWO))
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> configuration.code(dateValue)
    );
  }

  @Test
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationCheckboxMultipleDate.builder().build();
    final var value = ElementValueDateList.builder().dateList(List.of()).build();

    final var result = config.simplified(value);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnSimplifiedValue() {
    final var date1 = LocalDate.now();
    final var date2 = LocalDate.now().plusDays(1);

    final var configuration = ElementConfigurationCheckboxMultipleDate.builder()
        .id(new FieldId(FIELD_ID))
        .dates(
            List.of(
                CheckboxDate.builder()
                    .id(new FieldId(DATE_FIELD_ID))
                    .label(LABEL)
                    .code(new Code(CODE, CODE_SYSTEM, DISPLAY_NAME))
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId(DATE_FIELD_ID_TWO))
                    .label(LABEL_TWO)
                    .code(new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO))
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId(DATE_FIELD_ID_THREE))
                    .label(LABEL_THREE)
                    .code(new Code(CODE_THREE, CODE_SYSTEM, DISPLAY_NAME_THREE))
                    .build()
            )
        )
        .build();

    final var dateValueOne = ElementValueDate.builder()
        .dateId(new FieldId(DATE_FIELD_ID))
        .date(date1)
        .build();
    final var dateValueTwo = ElementValueDate.builder()
        .dateId(new FieldId(DATE_FIELD_ID_TWO))
        .date(date2)
        .build();
    final var value = ElementValueDateList.builder()
        .dateList(List.of(dateValueOne, dateValueTwo))
        .build();

    final var result = configuration.simplified(value);

    assertEquals(Optional.of(
            ElementSimplifiedValueLabeledList.builder()
                .list(
                    List.of(
                        ElementSimplifiedValueLabeledText.builder()
                            .label(LABEL)
                            .text(date1.toString())
                            .build(),
                        ElementSimplifiedValueLabeledText.builder()
                            .label(LABEL_TWO)
                            .text(date2.toString())
                            .build(),
                        ElementSimplifiedValueLabeledText.builder()
                            .label(LABEL_THREE)
                            .text("Ej angivet")
                            .build()
                    )
                )
                .build()),
        result
    );
  }
}