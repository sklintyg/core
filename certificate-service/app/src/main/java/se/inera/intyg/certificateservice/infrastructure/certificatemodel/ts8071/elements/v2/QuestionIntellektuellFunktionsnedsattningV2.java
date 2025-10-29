package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionIntellektuellFunktionsnedsattningV2 {

  public static final ElementId QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID = new ElementId(
      "26");
  public static final FieldId QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID = new FieldId(
      "26.1");

  private QuestionIntellektuellFunktionsnedsattningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionIntellektuellFunktionsnedsattningV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID)
                .name("Har personen någon intellektuell funktionsnedsättning?")
                .selectedText("Ja")
                .unselectedText("Nej")
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
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID,
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}

