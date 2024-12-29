package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class XmlGeneratorCodeListToBooleanTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String VALUE_ID = "ANSWER_ID";
  private static final String CODE_ID_ONE = "CODE_ID_ONE";
  private static final String CODE_ONE = "CODE_ONE";

  private static final String CODE_ID_TWO = "CODE_ID_TWO";
  private static final String CODE_TWO = "CODE_TWO";

  XmlGeneratorCodeListToBoolean xmlGeneratorCodeListToBoolean;

  @BeforeEach
  void setUp() {
    xmlGeneratorCodeListToBoolean = new XmlGeneratorCodeListToBoolean();
  }

  @Test
  void shallReturnEmptyIfValueIsWrongType() {
    final var elementData = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueText.builder()
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_ONE),
                            "label",
                            new Code(
                                CODE_ONE,
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        ))
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorCodeListToBoolean.generate(elementData,
        elementSpecification);

    assertTrue(actualResult.isEmpty());
  }

  @Test
  void shallThrowIllegalArgumentExceptionIfConfigIsWrongType() {
    final var elementData = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .list(Collections.emptyList())
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder()
                .build()
        )
        .build();

    assertThrows(IllegalArgumentException.class, () ->
        xmlGeneratorCodeListToBoolean.generate(elementData,
            elementSpecification));
  }

  @Test
  void shallReturnAnswerWithFalseValuesIfEmpty() {
    final var elementData = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .list(Collections.emptyList())
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_ONE),
                            "label",
                            new Code(
                                CODE_ONE,
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        ))
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorCodeListToBoolean.generate(elementData,
        elementSpecification);

    assertAll(
        () -> assertEquals(QUESTION_ID, actualResult.getFirst().getId()),
        () -> assertEquals(CODE_ID_ONE, actualResult.getFirst().getDelsvar().getFirst().getId()),
        () -> assertEquals("false",
            actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst())
    );
  }

  @Test
  void shallReturnAnswerWithOneDelsvar() {
    final var elementData = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        ElementValueCode.builder()
                            .codeId(new FieldId(CODE_ID_ONE))
                            .code(CODE_ONE)
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_ONE),
                            "label",
                            new Code(
                                CODE_ONE,
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        ))
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorCodeListToBoolean.generate(elementData,
        elementSpecification);

    assertAll(
        () -> assertEquals(QUESTION_ID, actualResult.getFirst().getId()),
        () -> assertEquals(CODE_ID_ONE, actualResult.getFirst().getDelsvar().getFirst().getId()),
        () -> assertEquals("true",
            actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst())
    );
  }

  @Test
  void shallReturnAnswerWithMultipleDelsvar() {
    final var elementData = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        ElementValueCode.builder()
                            .codeId(new FieldId(CODE_ID_ONE))
                            .code(CODE_ONE)
                            .build(),
                        ElementValueCode.builder()
                            .codeId(new FieldId(CODE_ID_TWO))
                            .code(CODE_TWO)
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_ONE),
                            "label",
                            new Code(
                                CODE_ONE,
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        ),
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_TWO),
                            "label",
                            new Code(
                                CODE_TWO,
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        )
                    )
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorCodeListToBoolean.generate(elementData,
        elementSpecification);

    assertAll(
        () -> assertEquals(QUESTION_ID, actualResult.getFirst().getId()),
        () -> assertEquals(CODE_ID_ONE, actualResult.getFirst().getDelsvar().getFirst().getId()),
        () -> assertEquals(CODE_ID_TWO, actualResult.getFirst().getDelsvar().getLast().getId()),
        () -> assertEquals("true",
            actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst()),
        () -> assertEquals("true",
            actualResult.getFirst().getDelsvar().getFirst().getContent().getLast())
    );
  }

  @Test
  void shallReturnAnswerWithMultipleDelsvarAndValueFalseIfValueIsMissing() {
    final var elementData = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        ElementValueCode.builder()
                            .codeId(new FieldId(CODE_ID_ONE))
                            .code(CODE_ONE)
                            .build(),
                        ElementValueCode.builder()
                            .codeId(new FieldId(CODE_ID_TWO))
                            .code(CODE_TWO)
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_ONE),
                            "label",
                            new Code(
                                "anotherValue1",
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        ),
                        new ElementConfigurationCode(
                            new FieldId(CODE_ID_TWO),
                            "label",
                            new Code(
                                "anotherValue2",
                                "CODE_SYSTEM",
                                "DISPLAY_NAME"
                            )
                        )
                    )
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorCodeListToBoolean.generate(elementData,
        elementSpecification);

    assertAll(
        () -> assertEquals(QUESTION_ID, actualResult.getFirst().getId()),
        () -> assertEquals(CODE_ID_ONE, actualResult.getFirst().getDelsvar().getFirst().getId()),
        () -> assertEquals(CODE_ID_TWO, actualResult.getFirst().getDelsvar().getLast().getId()),
        () -> assertEquals("false",
            actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst()),
        () -> assertEquals("false",
            actualResult.getFirst().getDelsvar().getFirst().getContent().getLast())
    );
  }
}