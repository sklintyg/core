package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.ANNAT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.UTLANDSKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselV1.QUESTION_HORSEL_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionHorselhjalpmedelV1 {

  public static final ElementId QUESTION_HORSELHJALPMEDEL_V1_ID = new ElementId("9.2");
  public static final FieldId QUESTION_HORSELHJALPMEDEL_V1_FIELD_ID = new FieldId("9.2");

  private QuestionHorselhjalpmedelV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselhjalpmedelV1(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HORSELHJALPMEDEL_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSELHJALPMEDEL_V1_FIELD_ID)
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
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_HORSELHJALPMEDEL_V1_ID,
                    QUESTION_HORSELHJALPMEDEL_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.showOrExist(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(
                        new FieldId(GR_II_III.code()), new FieldId(TAXI.code()),
                        new FieldId(UTLANDSKT.code()), new FieldId(FORLANG_GR_II_III.code()),
                        new FieldId(ANNAT.code())
                    )
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_HORSEL_V1_ID, null))
        .children(List.of(children))
        .shouldValidate(ElementDataPredicateFactory.codeList(
                QUESTION_INTYGET_AVSER_ID,
                List.of(new FieldId(GR_II_III.code()), new FieldId(TAXI.code()),
                    new FieldId(UTLANDSKT.code()), new FieldId(FORLANG_GR_II_III.code()),
                    new FieldId(ANNAT.code()))
            )
        )
        .build();
  }
}