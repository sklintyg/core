package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionHjartsjukdomBeskrivningV1 {

  public static final ElementId QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_ID = new ElementId(
      "11.2");
  public static final FieldId QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_FIELD_ID = new FieldId(
      "11.2");

  private QuestionHjartsjukdomBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHjartsjukdomBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_FIELD_ID)
                .name("Ange vilken sjukdom")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_HJARTSJUKDOM_ID,
                    QUESTION_HJARTSJUKDOM_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_ID,
                    QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_HJARTSJUKDOM_ID)
        )
        .mapping(new ElementMapping(QUESTION_HJARTSJUKDOM_ID, null))
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(250)
                    .build()
            )
        )
        .build();
  }
}
