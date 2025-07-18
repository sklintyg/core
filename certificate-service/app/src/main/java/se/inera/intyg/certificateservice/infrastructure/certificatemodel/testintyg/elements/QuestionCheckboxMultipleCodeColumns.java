package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;

public class QuestionCheckboxMultipleCodeColumns {

  public static final ElementId QUESTION_CHECKBOX_MULTIPLE_CODE_ID = new ElementId("3");
  public static final FieldId QUESTION_CHECKBOX_MULTIPLE_CODE_FIELD_ID = new FieldId("3");

  private QuestionCheckboxMultipleCodeColumns() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionCheckboxMultipleCodeColumns(
      ElementSpecification... children) {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(new Code("1", "test", "Test 1")),
        CodeFactory.elementConfigurationCode(new Code("2", "test", "Test 2")),
        CodeFactory.elementConfigurationCode(new Code("3", "test", "Test 3"))
    );

    return ElementSpecification.builder()
        .id(QUESTION_CHECKBOX_MULTIPLE_CODE_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_CHECKBOX_MULTIPLE_CODE_FIELD_ID)
                .name("Test av \"CheckboxMultipleCode Rows Layout\"")
                .elementLayout(ElementLayout.ROWS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_CHECKBOX_MULTIPLE_CODE_ID,
                    List.of(
                        new FieldId("1"),
                        new FieldId("2"),
                        new FieldId("3")
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }

}
