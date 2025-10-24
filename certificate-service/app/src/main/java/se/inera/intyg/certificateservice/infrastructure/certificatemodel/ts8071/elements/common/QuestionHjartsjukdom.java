package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionHjartsjukdom {

  public static final ElementId QUESTION_HJARTSJUKDOM_ID = new ElementId("11");
  public static final FieldId QUESTION_HJARTSJUKDOM_FIELD_ID = new FieldId("11.1");

  private QuestionHjartsjukdom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHjartsjukdom(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HJARTSJUKDOM_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HJARTSJUKDOM_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har eller har personen haft någon hjärt- eller kärlsjukdom?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_HJARTSJUKDOM_ID,
                    QUESTION_HJARTSJUKDOM_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}