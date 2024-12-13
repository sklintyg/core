package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionHorsel {

  public static final ElementId QUESTION_HORSEL_ID = new ElementId("9");
  public static final FieldId QUESTION_HORSEL_FIELD_ID = new FieldId("9.1");

  private QuestionHorsel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorsel() {
    return ElementSpecification.builder()
        .id(QUESTION_HORSEL_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSEL_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd? Hörapparat får användas.")
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
                    QUESTION_HORSEL_ID,
                    QUESTION_HORSEL_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_INTYGET_AVSER_ID,
                    new RuleExpression(
                        String.format(
                            "exists(%s) || exists(%s)", GR_II_III.code(), TAXI.code()
                        )
                    )
                )
            )
        )
        .shouldValidate(ShouldValidateFactory.codeList(
                QUESTION_INTYGET_AVSER_ID,
                List.of(new FieldId(GR_II_III.code()), new FieldId(TAXI.code()))
            )
        )
        .build();
  }
}