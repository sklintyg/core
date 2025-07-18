package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationVisualAcuities;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVisualAcuities {

  private QuestionVisualAcuities() {
    throw new IllegalStateException("Utility class");
  }

  public static final ElementId QUESTION_VISUAL_ACUITIES_ID = new ElementId("14");
  public static final FieldId QUESTION_VISUAL_ACUITIES_FIELD_ID = new FieldId("14");
  public static final String TEST1_ID = "14.1";
  public static final String TEST2_ID = "14.2";
  public static final String TEST3_ID = "14.3";
  public static final String TEST4_ID = "14.4";
  public static final String TEST5_ID = "14.5";
  public static final String TEST6_ID = "14.6";

  public static ElementSpecification questionVisualAcuities() {
    return ElementSpecification.builder()
        .id(QUESTION_VISUAL_ACUITIES_ID)
        .configuration(
            ElementConfigurationVisualAcuities.builder()
                .id(QUESTION_VISUAL_ACUITIES_FIELD_ID)
                .name("Test av \"VISUAL_ACUITIES\"")
                .withCorrectionLabel("Med \"correction label\"")
                .withoutCorrectionLabel("Utan \"correction label\"")
                .min(0.0)
                .max(2.0)
                .rightEye(
                    ElementVisualAcuity.builder()
                        .label("Höger test")
                        .withoutCorrectionId(TEST1_ID)
                        .withCorrectionId(TEST2_ID)
                        .build()
                )
                .leftEye(
                    ElementVisualAcuity.builder()
                        .label("Vänster test")
                        .withoutCorrectionId(TEST3_ID)
                        .withCorrectionId(TEST4_ID)
                        .build()
                )
                .binocular(
                    ElementVisualAcuity.builder()
                        .label("Båda test")
                        .withoutCorrectionId(TEST5_ID)
                        .withCorrectionId(TEST6_ID)
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
                CertificateElementRuleFactory.mandatoryAndExist(
                    QUESTION_VISUAL_ACUITIES_ID,
                    List.of(
                        new FieldId(TEST1_ID),
                        new FieldId(TEST3_ID),
                        new FieldId(TEST5_ID)
                    )
                )
            )
        )
        .build();
  }
}