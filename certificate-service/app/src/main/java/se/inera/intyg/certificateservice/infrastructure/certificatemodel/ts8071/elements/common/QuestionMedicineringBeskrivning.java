package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicinering.QUESTION_MEDICINERING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicinering.QUESTION_MEDICINERING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMedicineringBeskrivning {

  public static final ElementId QUESTION_MEDICINERING_BESKRIVNING_ID = new ElementId(
      "21.2");
  public static final FieldId QUESTION_MEDICINERING_BESKRIVNING_FIELD_ID = new FieldId(
      "21.2");

  private QuestionMedicineringBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicineringBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINERING_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_MEDICINERING_BESKRIVNING_FIELD_ID)
                .name("Ange vilken eller vilka mediciner")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MEDICINERING_ID,
                    QUESTION_MEDICINERING_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MEDICINERING_BESKRIVNING_ID,
                    QUESTION_MEDICINERING_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MEDICINERING_BESKRIVNING_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_MEDICINERING_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_MEDICINERING_ID, null)
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(50)
                    .build()
            )
        )
        .build();
  }
}
