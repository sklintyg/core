package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionStroke.QUESTION_STROKE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionStroke.QUESTION_STROKE_ID;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKnowledge;

public class QuestionStrokePavarkan {

  public static final ElementId QUESTION_STROKE_PAVARKAN_ID = new ElementId(
      "11.10");
  public static final FieldId QUESTION_STROKE_PAVARKAN_FIELD_ID = new FieldId(
      "11.10");

  private QuestionStrokePavarkan() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionStrokePavarkan(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKnowledge.YES),
        CodeFactory.elementConfigurationCode(CodeSystemKnowledge.NO),
        CodeFactory.elementConfigurationCode(CodeSystemKnowledge.NO_KNOWLEDGE)
    );

    return ElementSpecification.builder()
        .id(QUESTION_STROKE_PAVARKAN_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_STROKE_PAVARKAN_FIELD_ID)
                .name(
                    "Om stroke förekommit, har den inträffat i/påverkat syncentrum (occipitalloben eller synnerven)?")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_STROKE_PAVARKAN_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_STROKE_ID,
                    QUESTION_STROKE_FIELD_ID
                )
            )
        )
        .shouldValidate(ShouldValidateFactory.radioBoolean(QUESTION_STROKE_ID))
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
