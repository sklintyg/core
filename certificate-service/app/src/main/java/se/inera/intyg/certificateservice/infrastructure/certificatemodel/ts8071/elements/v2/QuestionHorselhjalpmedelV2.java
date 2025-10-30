package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselV2.QUESTION_HORSEL_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionHorselhjalpmedelV2 {

  public static final ElementId QUESTION_HORSELHJALPMEDEL_V2_ID = new ElementId("9.2");
  public static final FieldId QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID = new FieldId("9.2");

  private QuestionHorselhjalpmedelV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselhjalpmedelV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HORSELHJALPMEDEL_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Behöver personen använda hörapparat för att kunna uppfatta vanlig samtalsstämma på fyra meters avstånd?")
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
                    QUESTION_HORSELHJALPMEDEL_V2_ID,
                    QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID
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
        .mapping(
            new ElementMapping(QUESTION_HORSEL_V2_ID, null)
        )
        .shouldValidate(ElementDataPredicateFactory.codeList(
                QUESTION_INTYGET_AVSER_ID,
                List.of(
                    new FieldId(GR_II_III.code()),
                    new FieldId(FORLANG_GR_II_III.code()),
                    new FieldId(TAXI.code())
                )
            )
        )
        .children(
            List.of(children)
        )
        .build();
  }
}

