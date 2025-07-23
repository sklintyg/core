package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;

public class QuestionRadioMultipleCode {

  public static final ElementId QUESTION_RADIO_MULTIPLE_CODE_ID = new ElementId("11");
  public static final FieldId QUESTION_RADIO_MULTIPLE_CODE_FIELD_ID = new FieldId("11.1");

  private QuestionRadioMultipleCode() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionRadioMultipleCode(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        CodeFactory.elementConfigurationCode(new Code("TEST_1", "TEST", "TEST 1")),
        CodeFactory.elementConfigurationCode(new Code("TEST_2", "TEST", "TEST 2")),
        CodeFactory.elementConfigurationCode(new Code("TEST_3", "TEST", "TEST 3")),
        CodeFactory.elementConfigurationCode(new Code("TEST_4", "TEST", "TEST 4"))
    );

    return ElementSpecification.builder()
        .id(QUESTION_RADIO_MULTIPLE_CODE_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_RADIO_MULTIPLE_CODE_FIELD_ID)
                .name("RADIO_MULTIPLE_CODE")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}