package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionHorselV2 {

  public static final ElementId QUESTION_HORSEL_V2_ID = new ElementId("9");
  public static final FieldId QUESTION_HORSEL_V2_FIELD_ID = new FieldId("9.1");

  private QuestionHorselV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselV2() {
    return ElementSpecification.builder()
        .id(QUESTION_HORSEL_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSEL_V2_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd (hörapparat får användas)?")
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
                    QUESTION_HORSEL_V2_ID,
                    QUESTION_HORSEL_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_INTYGET_AVSER_ID,
                    new RuleExpression(
                        String.format(
                            "exists(%s) || exists(%s) || exists(%s)",
                            GR_II_III.code(), FORLANG_GR_II_III.code(), TAXI.code()
                        )
                    )
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.codeList(
                QUESTION_INTYGET_AVSER_ID,
                List.of(
                    new FieldId(GR_II_III.code()), new FieldId(FORLANG_GR_II_III.code()),
                    new FieldId(TAXI.code())
                )
            )
        )
        .build();
  }
}

