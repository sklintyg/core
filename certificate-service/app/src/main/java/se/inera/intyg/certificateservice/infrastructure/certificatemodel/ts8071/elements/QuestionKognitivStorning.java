package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionKognitivStorning {

  public static final ElementId QUESTION_KOGNITIV_STORNING_ID = new ElementId("16");
  public static final FieldId QUESTION_KOGNITIV_STORNING_FIELD_ID = new FieldId("16.1");

  private QuestionKognitivStorning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKognitivStorning(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_KOGNITIV_STORNING_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_KOGNITIV_STORNING_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har personen diagnos allvarlig kognitiv störning?")
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
                    QUESTION_KOGNITIV_STORNING_ID,
                    QUESTION_KOGNITIV_STORNING_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
