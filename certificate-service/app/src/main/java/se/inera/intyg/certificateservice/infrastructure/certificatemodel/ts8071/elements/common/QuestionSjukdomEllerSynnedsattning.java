package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSjukdomEllerSynnedsattning {

  public static final ElementId QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID = new ElementId("7");
  public static final FieldId QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID = new FieldId("7.1");

  private QuestionSjukdomEllerSynnedsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomEllerSynnedsattning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID)
                .description(
                    "Exempel på vanligt förekommande ögonsjukdomar är glaukom, retinopati och retinitis pigmentosa. Exempel på synnedsättning kan vara "
                        + "dubbelseende, syn med enbart ett öga eller plötsligt nedsatt synskärpa.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Finns uppgift om ögonsjukdom eller synnedsättning?")
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
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID,
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID
                ),
                CertificateElementRuleFactory.showIfNot(
                    QUESTION_SYNFUNKTIONER_ID,
                    QUESTION_SYNFUNKTIONER_FIELD_ID
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_SYNFUNKTIONER_ID, false))
        .children(List.of(children))
        .build();
  }
}