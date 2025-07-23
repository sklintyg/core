package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;

public class QuestionDropdown {

  public static final ElementId QUESTION_DROPDOWN_ID = new ElementId("8");
  private static final FieldId QUESTION_DROPDOWN_FIELD_ID = new FieldId("8.1");


  private QuestionDropdown() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDropdown(
      ElementSpecification... children) {
    final var dropdownItems = List.of(
        new ElementConfigurationCode(
            new FieldId(""),
            "Välj i listan",
            null
        ),
        new ElementConfigurationCode(
            new FieldId("Test1"),
            "TEST 1",
            new Code("TEST_1", "TEST", "TEST 1")
        ),
        new ElementConfigurationCode(
            new FieldId("Test2"),
            "TEST 2",
            new Code("TEST_2", "TEST", "TEST 2")
        ),
        new ElementConfigurationCode(
            new FieldId("Test3"),
            "TEST 3",
            new Code("TEST_3", "TEST", "TEST 3")
        ),
        new ElementConfigurationCode(
            new FieldId("Test4"),
            "TEST 4",
            new Code("TEST_4", "TEST", "TEST 4")
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_DROPDOWN_ID)
        .configuration(
            ElementConfigurationDropdownCode.builder()
                .id(QUESTION_DROPDOWN_FIELD_ID)
                .name("DROPDOWN")
                .list(dropdownItems)
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