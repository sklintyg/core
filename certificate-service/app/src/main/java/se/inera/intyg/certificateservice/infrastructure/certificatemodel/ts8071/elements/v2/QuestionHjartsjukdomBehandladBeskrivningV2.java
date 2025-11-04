package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdomBehandlad.QUESTION_HJARTSJUKDOM_BEHANDLAD_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdomBehandlad.QUESTION_HJARTSJUKDOM_BEHANDLAD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionHjartsjukdomBehandladBeskrivningV2 {

  public static final ElementId QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_ID = new ElementId(
      "11.4");
  public static final FieldId QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_FIELD_ID = new FieldId(
      "11.4");
  private static final int TEXT_LIMIT = 250;

  private QuestionHjartsjukdomBehandladBeskrivningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHjartsjukdomBehandladBeskrivningV2() {
    return ElementSpecification.builder()
        .id(QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_FIELD_ID)
                .name("Ange när och hur")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_ID,
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_ID,
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V2_ID, (short) TEXT_LIMIT
                )
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_HJARTSJUKDOM_BEHANDLAD_ID)
        )
        .mapping(new ElementMapping(QUESTION_HJARTSJUKDOM_ID, null))
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(TEXT_LIMIT)
                    .build()
            )
        )
        .build();
  }
}

