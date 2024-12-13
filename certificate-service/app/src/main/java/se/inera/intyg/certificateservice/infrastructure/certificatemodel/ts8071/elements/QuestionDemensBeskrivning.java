package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionDemens.QUESTION_DEMENS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionDemens.QUESTION_DEMENS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionKognitivStorning.QUESTION_KOGNITIV_STORNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionDemensBeskrivning {

  public static final ElementId QUESTION_DEMENS_BESKRIVNING_ID = new ElementId(
      "16.3");
  public static final FieldId QUESTION_DEMENS_BESKRIVNING_FIELD_ID = new FieldId(
      "16.3");

  private QuestionDemensBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDemensBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_DEMENS_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_DEMENS_BESKRIVNING_FIELD_ID)
                .name(
                    "Ange vilka tecken, eventuell diagnos och grad? (Med grader avses lindrig, måttlig/medelsvår eller grav/allvarlig.)")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_DEMENS_ID,
                    QUESTION_DEMENS_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_DEMENS_BESKRIVNING_ID,
                    QUESTION_DEMENS_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_DEMENS_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_DEMENS_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_KOGNITIV_STORNING_ID, null)
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
