package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselV2.QUESTION_HORSEL_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselhjalpmedelV2.QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselhjalpmedelV2.QUESTION_HORSELHJALPMEDEL_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat;

public class QuestionHorselhjalpmedelPositionV2 {

  public static final ElementId QUESTION_HORSELHJALPMEDEL_POSITION_V2_ID = new ElementId(
      "9.3");
  public static final FieldId QUESTION_HORSELHJALPMEDEL_POSITION_V2_FIELD_ID = new FieldId(
      "9.3");

  private QuestionHorselhjalpmedelPositionV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselhjalpmedelPositionV2(
      ElementSpecification... children) {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvAnatomiskLokalisationHorapparat.HOGER),
        CodeFactory.elementConfigurationCode(CodeSystemKvAnatomiskLokalisationHorapparat.VANSTER)
    );

    return ElementSpecification.builder()
        .id(QUESTION_HORSELHJALPMEDEL_POSITION_V2_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_HORSELHJALPMEDEL_POSITION_V2_FIELD_ID)
                .name("Om personen behöver använda hörapparat, ange på vilket öra")
                .elementLayout(ElementLayout.INLINE)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_HORSELHJALPMEDEL_POSITION_V2_ID,
                    checkboxes.stream().map(ElementConfigurationCode::id).toList()
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_HORSELHJALPMEDEL_V2_ID,
                    QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_HORSELHJALPMEDEL_V2_ID))
        .mapping(new ElementMapping(QUESTION_HORSEL_V2_ID, null))
        .children(List.of(children))
        .build();
  }
}

