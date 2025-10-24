package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationVisualAcuities;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSynskarpa {

  private QuestionSynskarpa() {
    throw new IllegalStateException("Utility class");
  }

  public static final ElementId QUESTION_SYNSKARPA_ID = new ElementId("5");
  public static final FieldId QUESTION_SYNSKARPA_FIELD_ID = new FieldId("5");
  public static final String RIGHT_EYE_WITHOUT_CORRECTION_ID = "5.1";
  public static final String RIGHT_EYE_WITH_CORRECTION_ID = "5.4";
  public static final String LEFT_EYE_WITHOUT_CORRECTION_ID = "5.2";
  public static final String LEFT_EYE_WITH_CORRECTION_ID = "5.5";
  public static final String BINOCULAR_WITHOUT_CORRECTION_ID = "5.3";
  public static final String BINOCULAR_WITH_CORRECTION_ID = "5.6";

  public static ElementSpecification questionSynskarpa() {
    return ElementSpecification.builder()
        .id(QUESTION_SYNSKARPA_ID)
        .configuration(
            ElementConfigurationVisualAcuities.builder()
                .id(QUESTION_SYNSKARPA_FIELD_ID)
                .name("Synskärpa")
                .withCorrectionLabel("Med korrektion")
                .withoutCorrectionLabel("Utan korrektion")
                .min(0.0)
                .max(2.0)
                .rightEye(
                    ElementVisualAcuity.builder()
                        .label("Höger öga")
                        .withoutCorrectionId(RIGHT_EYE_WITHOUT_CORRECTION_ID)
                        .withCorrectionId(RIGHT_EYE_WITH_CORRECTION_ID)
                        .build()
                )
                .leftEye(
                    ElementVisualAcuity.builder()
                        .label("Vänster öga")
                        .withoutCorrectionId(LEFT_EYE_WITHOUT_CORRECTION_ID)
                        .withCorrectionId(LEFT_EYE_WITH_CORRECTION_ID)
                        .build()
                )
                .binocular(
                    ElementVisualAcuity.builder()
                        .label("Binokulärt")
                        .withoutCorrectionId(BINOCULAR_WITHOUT_CORRECTION_ID)
                        .withCorrectionId(BINOCULAR_WITH_CORRECTION_ID)
                        .build()
                )
                .build()
        )
        .validations(
            List.of(
                ElementValidationVisualAcuities.builder()
                    .mandatory(true)
                    .min(0.0)
                    .max(2.0)
                    .minAllowedSightOneEye(0.1)
                    .minAllowedSightOtherEye(0.8)
                    .build()
            )

        )
        .rules(
            List.of(
                CertificateElementRuleFactory.showIfNot(
                    QUESTION_SYNFUNKTIONER_ID,
                    QUESTION_SYNFUNKTIONER_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatoryAndExist(
                    QUESTION_SYNSKARPA_ID,
                    List.of(
                        new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID),
                        new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID),
                        new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID)
                    )
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_SYNFUNKTIONER_ID, false))
        .build();
  }
}