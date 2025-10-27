package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionPsykiskBeskrivning {

  public static final ElementId QUESTION_PSYKISK_BESKRIVNING_ID = new ElementId(
      "19.2");
  public static final FieldId QUESTION_PSYKISK_BESKRIVNING_FIELD_ID = new FieldId(
      "19.2");

  private QuestionPsykiskBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskBeskrivning(ElementId parentElementId,
      FieldId parentFieldId) {
    return ElementSpecification.builder()
        .id(QUESTION_PSYKISK_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PSYKISK_BESKRIVNING_FIELD_ID)
                .name("Ange vilken sjukdom eller störning")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    parentElementId,
                    parentFieldId
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PSYKISK_BESKRIVNING_ID,
                    QUESTION_PSYKISK_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PSYKISK_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(parentElementId)
        )
        .mapping(
            new ElementMapping(parentElementId, null)
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
