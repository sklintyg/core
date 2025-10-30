package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.LEFT_EYE_WITHOUT_CORRECTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.RIGHT_EYE_WITHOUT_CORRECTION_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionToleransKorrektionV2 {

  public static final ElementId QUESTION_TOLERANS_KORREKTION_V2_ID = new ElementId("24");
  public static final FieldId QUESTION_TOLERANS_KORREKTION_V2_FIELD_ID = new FieldId("24.1");

  private QuestionToleransKorrektionV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionToleransKorrektionV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_TOLERANS_KORREKTION_V2_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_TOLERANS_KORREKTION_V2_FIELD_ID)
                .name("Ange eventuella problem med tolerans av korrektionen")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.visualAcuities(
                    QUESTION_SYNSKARPA_ID,
                    new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID),
                    new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID)
                ),
                CertificateElementRuleFactory.limit(QUESTION_TOLERANS_KORREKTION_V2_ID, (short) 250)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(250)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.visualAcuities(QUESTION_SYNSKARPA_ID, 0.8, 0.1)
        )
        .children(List.of(children))
        .build();
  }
}

