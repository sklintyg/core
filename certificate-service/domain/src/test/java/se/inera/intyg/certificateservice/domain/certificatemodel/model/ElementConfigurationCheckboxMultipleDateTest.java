package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class ElementConfigurationCheckboxMultipleDateTest {

  private static final String FIELD_ID = "FIELD_ID";
  private static final String DATE_FIELD_ID = "DATE_FIELD_ID";
  private static final String DATE_FIELD_ID_TWO = "DATE_FIELD_ID_TWO";
  private static final String LABEL = "LABEL";
  private static final String LABEL_TWO = "LABEL_TWO";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String CODE = "CODE";
  private static final String DISPLAY_NAME = "DISPLAY_NAME";
  private static final String CODE_TWO = "CODE_TWO";
  private static final String DISPLAY_NAME_TWO = "DISPLAY_NAME_TWO";

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
                new CheckboxDate(
                    new FieldId(DATE_FIELD_ID),
                    LABEL,
                    new Code(CODE, CODE_SYSTEM, DISPLAY_NAME)
                ),
                new CheckboxDate(
                    new FieldId(DATE_FIELD_ID_TWO),
                    LABEL_TWO,
                    new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO)
                )
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
}