package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionStrokeV1.QUESTION_STROKE_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionStrokeV1.QUESTION_STROKE_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001;

public class QuestionStrokePaverkanV1 {

  public static final ElementId QUESTION_STROKE_PAVERKAN_V1_ID = new ElementId(
      "11.10");
  public static final FieldId QUESTION_STROKE_PAVERKAN_FIELD_V1_ID = new FieldId(
      "11.10");

  private QuestionStrokePaverkanV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionStrokePaverkanV1(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvTs001.JA),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs001.NEJ),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs001.VET_EJ)
    );

    return ElementSpecification.builder()
        .id(QUESTION_STROKE_PAVERKAN_V1_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_STROKE_PAVERKAN_FIELD_V1_ID)
                .name(
                    "Om stroke förekommit, har den inträffat i/påverkat syncentrum (occipitalloben eller synnerven)?")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_STROKE_PAVERKAN_V1_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_STROKE_V1_ID,
                    QUESTION_STROKE_V1_FIELD_ID
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_STROKE_V1_ID))
        .mapping(new ElementMapping(QUESTION_HJARTSJUKDOM_ID, null))
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}