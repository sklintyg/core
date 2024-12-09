package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynkope.QUESTION_SYNKOPE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynkope.QUESTION_SYNKOPE_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionSynkopeBeskrivning {

  public static final ElementId QUESTION_SYNKOPE_BESKRIVNING_ID = new ElementId(
      "11.8");
  public static final FieldId QUESTION_SYNKOPE_BESKRIVNING_FIELD_ID = new FieldId(
      "11.8");

  private QuestionSynkopeBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSynkopeBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_SYNKOPE_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_SYNKOPE_BESKRIVNING_FIELD_ID)
                .name("Ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SYNKOPE_ID,
                    QUESTION_SYNKOPE_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SYNKOPE_BESKRIVNING_ID,
                    QUESTION_SYNKOPE_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SYNKOPE_BESKRIVNING_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_SYNKOPE_ID)
        )
        .mapping(new ElementMapping(QUESTION_SYNKOPE_ID, null))
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
