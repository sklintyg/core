package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSjukdomshistorik {

  public static final ElementId QUESTION_SJUKDOMSHISTORIK_ID = new ElementId("7.3");
  public static final FieldId QUESTION_SJUKDOMSHISTORIK_FIELD_ID = new FieldId("7.3");

  private QuestionSjukdomshistorik() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomshistorik(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOMSHISTORIK_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SJUKDOMSHISTORIK_FIELD_ID)
                .description(
                    "Exempel på sjukdomshistorik och andra omständigheter som kan påverka synfunktionerna är stroke och laserbehandling av retinopati. Det "
                        + "kan också vara skalltrauma, hjärntumör eller prematur födsel som är av sådan grad att den kan ha påverkan på synfältet.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Finns uppgift om annan sjukdomshistorik eller andra omständigheter som kan indikera påverkan på synfunktionerna?")
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
                    QUESTION_SJUKDOMSHISTORIK_ID,
                    QUESTION_SJUKDOMSHISTORIK_FIELD_ID
                ),
                CertificateElementRuleFactory.showIfNot(
                    QUESTION_SYNFUNKTIONER_ID,
                    QUESTION_SYNFUNKTIONER_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID, null))
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_SYNFUNKTIONER_ID, false))
        .children(List.of(children))
        .build();
  }
}