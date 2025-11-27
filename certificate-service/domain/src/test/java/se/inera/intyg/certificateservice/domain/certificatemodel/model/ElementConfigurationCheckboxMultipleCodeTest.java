package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class ElementConfigurationCheckboxMultipleCodeTest {

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
  void shallReturnSimplifiedValue() {
    final var configuration = ElementConfigurationCheckboxMultipleCode.builder()
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

    final var codeValue = ElementValueCodeList.builder()
        .list(
            List.of(
                ElementValueCode.builder()
                    .codeId(new FieldId(CODE_FIELD_ID))
                    .code(CODE)
                    .build(),
                ElementValueCode.builder()
                    .codeId(new FieldId(CODE_FIELD_ID_TWO))
                    .code(CODE_TWO)
                    .build()
            )
        )
        .build();

    assertEquals(
        ElementSimplifiedValueList.builder()
            .list(List.of(DISPLAY_NAME, DISPLAY_NAME_TWO))
            .build(),
        configuration.simplified(codeValue).get()
    );
  }

  @Test
  void shouldReturnSimplifiedValueIfEmptyList() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationCheckboxMultipleCode.builder()
        .list(Collections.emptyList())
        .build();

    assertEquals(expected, config.simplified(config.emptyValue()));
  }

  @Test
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationCheckboxMultipleCode.builder()
        .build();

    assertEquals(expected, config.simplified(config.emptyValue()));
  }
}