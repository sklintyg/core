package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionKognitivStorningV2.QUESTION_KOGNITIV_STORNING_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDemensV2 {

  public static final ElementId QUESTION_DEMENS_V2_ID = new ElementId("16.2");
  public static final FieldId QUESTION_DEMENS_V2_FIELD_ID = new FieldId("16.2");

  private QuestionDemensV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDemensV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_DEMENS_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_DEMENS_V2_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen diagnos demens eller annan kognitiv störning eller finns tecken på demens eller andra kognitiva störningar?")
                .description(
                    "Med demens avses diagnos ställd utifrån vedertagen praxis eller utifrån de kriterier som anges i DSM-IV, DSM-V eller ICD-10. Med kognitiv störning avses kognitiv störning/svikt som inte är demens. Med grader avses lindrig, måttlig/medelsvår eller grav/allvarlig.")
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
                    QUESTION_DEMENS_V2_ID,
                    QUESTION_DEMENS_V2_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_KOGNITIV_STORNING_V2_ID, null))
        .children(List.of(children))
        .build();
  }
}

