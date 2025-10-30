package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDemensV1.QUESTION_DEMENS_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDemensV1.QUESTION_DEMENS_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKognitivStorningV1.QUESTION_KOGNITIV_STORNING_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionDemensBeskrivningV1 {

  public static final ElementId QUESTION_DEMENS_BESKRIVNING_V1_ID = new ElementId(
      "16.3");
  public static final FieldId QUESTION_DEMENS_BESKRIVNING_V1_FIELD_ID = new FieldId(
      "16.3");

  private QuestionDemensBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDemensBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_DEMENS_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_DEMENS_BESKRIVNING_V1_FIELD_ID)
                .name(
                    "Ange vilka tecken, eventuell diagnos och grad? (Med grader avses lindrig, måttlig/medelsvår eller grav/allvarlig.)")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_DEMENS_V1_ID,
                    QUESTION_DEMENS_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_DEMENS_BESKRIVNING_V1_ID,
                    QUESTION_DEMENS_BESKRIVNING_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_DEMENS_BESKRIVNING_V1_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_DEMENS_V1_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_KOGNITIV_STORNING_V1_ID, null)
        )
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
