package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class ElementConfigurationRadioMultipleCodeTest {

  private static final String FIELD_ID = "FIELD_ID";
  private static final String CODE_FIELD_ID = "CODE_FIELD_ID";
  private static final String CODE_FIELD_ID_TWO = "CODE_FIELD_ID_TWO";
  private static final String LABEL = "LABEL";
  private static final String LABEL_TWO = "LABEL_TWO";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String CODE = "CODE";
  private static final String DISPLAY_NAME = "DISPLAY_NAME";
  private static final String CODE_TWO = "CODE_TWO";
  private static final String DISPLAY_NAME_TWO = "DISPLAY_NAME_TWO";

  @Test
  void shallReturnEmptyValue() {
    final var emptyValue = ElementValueCode.builder()
        .codeId(new FieldId(FIELD_ID))
        .build();

    final var configuration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId(FIELD_ID))
        .build();

    assertEquals(emptyValue, configuration.emptyValue());
  }

  @Test
  void shallReturnMatchingCode() {
    final var expectedCode = new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO);

    final var configuration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId(FIELD_ID))
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(CODE_FIELD_ID),
                    LABEL,
                    new Code(CODE, CODE_SYSTEM, DISPLAY_NAME)
                ),
                new ElementConfigurationCode(
                    new FieldId(CODE_FIELD_ID_TWO),
                    LABEL_TWO,
                    new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO)
                )
            )
        )
        .build();

    final var codeValue = ElementValueCode.builder()
        .codeId(new FieldId(CODE_FIELD_ID_TWO))
        .code(CODE_TWO)
        .build();

    assertEquals(expectedCode, configuration.code(codeValue));
  }


  @Test
  void shallReturnSimplifiedValue() {
    final var configuration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId(FIELD_ID))
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(CODE_FIELD_ID),
                    LABEL,
                    new Code(CODE, CODE_SYSTEM, DISPLAY_NAME)
                ),
                new ElementConfigurationCode(
                    new FieldId(CODE_FIELD_ID_TWO),
                    LABEL_TWO,
                    new Code(CODE_TWO, CODE_SYSTEM, DISPLAY_NAME_TWO)
                )
            )
        )
        .build();

    final var codeValue = ElementValueCode.builder()
        .codeId(new FieldId(CODE_FIELD_ID_TWO))
        .code(CODE_TWO)
        .build();

    assertEquals(
        ElementSimplifiedValueText.builder()
            .text(DISPLAY_NAME_TWO)
            .build(),
        configuration.simplified(codeValue).get()
    );
  }

  @Test
  void shallThrowIfNoMatchingCode() {
    final var configuration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId(FIELD_ID))
        .list(Collections.emptyList())
        .build();

    final var codeValue = ElementValueCode.builder()
        .codeId(new FieldId(CODE_FIELD_ID_TWO))
        .code(CODE_TWO)
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> configuration.code(codeValue)
    );
  }

  @Test
  void shouldReturnSimplifiedValueForEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationRadioMultipleCode.builder()
        .build();

    assertEquals(expected, config.simplified(config.emptyValue()));
  }
}