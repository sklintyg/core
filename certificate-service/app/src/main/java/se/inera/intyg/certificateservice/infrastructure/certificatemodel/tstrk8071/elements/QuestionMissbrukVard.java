package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbruk.QUESTION_MISSBRUK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMissbrukVard {

  public static final ElementId QUESTION_MISSBRUK_VARD_ID = new ElementId("18.6");
  public static final FieldId QUESTION_MISSBRUK_VARD_FIELD_ID = new FieldId("18.6");

  private QuestionMissbrukVard() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukVard(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_VARD_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MISSBRUK_VARD_FIELD_ID)
                .name(
                    "Har personen vid något tillfälle vårdats eller sökt hjälp för missbruk eller beroende av alkohol, narkotika eller läkemedel?")
                .description(
                    "Här avses exempelvis stödinsatser som vård på behandlingshem, tvångsvård eller deltagande i substitutionsbehandlingsprogram såsom LARO. Beskriv vilken typ av insats det rör sig om.")
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
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_MISSBRUK_VARD_ID,
                    QUESTION_MISSBRUK_VARD_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_MISSBRUK_ID, null))
        .children(List.of(children))
        .build();
  }
}