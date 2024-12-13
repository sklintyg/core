package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionKognitivStorning.QUESTION_KOGNITIV_STORNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDemens {

  public static final ElementId QUESTION_DEMENS_ID = new ElementId("16.2");
  public static final FieldId QUESTION_DEMENS_FIELD_ID = new FieldId("16.2");

  private QuestionDemens() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDemens(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_DEMENS_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_DEMENS_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen diagnos demens eller annan kognitiv störning eller finns tecken på demens eller andra kognitiva störningar?")
                .description(
                    "Med demens avses diagnos ställd utifrån vedertagen praxis eller utifrån de kriterier som anges i DSM-IV, DSM-V eller ICD-10. Med kognitiv störning avses kognitiv störning/svikt som inte är demens.")
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
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_DEMENS_ID,
                    QUESTION_DEMENS_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_KOGNITIV_STORNING_ID, null))
        .children(List.of(children))
        .build();
  }
}