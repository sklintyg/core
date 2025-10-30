package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionEpilepsiBeskrivning {

  public static final ElementId QUESTION_EPILEPSI_BESKRIVNING_ID = new ElementId(
      "14.2");
  public static final FieldId QUESTION_EPILEPSI_BESKRIVNING_FIELD_ID = new FieldId(
      "14.2");

  private QuestionEpilepsiBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionEpilepsiBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_EPILEPSI_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_EPILEPSI_BESKRIVNING_FIELD_ID)
                .name("Ange tidpunkt för senaste epileptiska anfall")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_EPILEPSI_ID,
                    QUESTION_EPILEPSI_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_EPILEPSI_BESKRIVNING_ID,
                    QUESTION_EPILEPSI_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_EPILEPSI_BESKRIVNING_ID,
                    (short) 50
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_EPILEPSI_ID))
        .mapping(
            new ElementMapping(QUESTION_EPILEPSI_ID, null)
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
