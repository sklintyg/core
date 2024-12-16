package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.ANNAT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.FORLANG_GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.UTLANDSKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorsel.QUESTION_HORSEL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionHorselhjalpmedel {

  public static final ElementId QUESTION_HORSELHJALPMEDEL_ID = new ElementId("9.2");
  public static final FieldId QUESTION_HORSELHJALPMEDEL_FIELD_ID = new FieldId("9.2");

  private QuestionHorselhjalpmedel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselhjalpmedel(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HORSELHJALPMEDEL_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSELHJALPMEDEL_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Behöver hörapparat användas?")
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
                    QUESTION_HORSELHJALPMEDEL_ID,
                    QUESTION_HORSELHJALPMEDEL_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_INTYGET_AVSER_ID,
                    new RuleExpression(
                        String.format(
                            "!exists(%s) && !exists(%s)", GR_II.code(), FORLANG_GR_II.code()
                        )
                    )
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_HORSEL_ID, null))
        .children(List.of(children))
        .shouldValidate(ShouldValidateFactory.codeList(
                QUESTION_INTYGET_AVSER_ID,
                List.of(new FieldId(GR_II_III.code()), new FieldId(TAXI.code()),
                    new FieldId(UTLANDSKT.code()), new FieldId(FORLANG_GR_II_III.code()),
                    new FieldId(ANNAT.code()))
            )
        )
        .build();
  }
}