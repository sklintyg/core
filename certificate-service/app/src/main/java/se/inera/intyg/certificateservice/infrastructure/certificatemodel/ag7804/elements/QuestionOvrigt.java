package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionOvrigt {

  public static final ElementId QUESTION_OVRIGT_ID = new ElementId(
      "25");
  private static final FieldId QUESTION_OVRIGT_FIELD_ID = new FieldId("25.1");

  private QuestionOvrigt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionOvrigt() {
    return ElementSpecification.builder()
        .id(QUESTION_OVRIGT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_OVRIGT_FIELD_ID)
                .name(
                    "Övriga upplysningar")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_OVRIGT_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }
}
