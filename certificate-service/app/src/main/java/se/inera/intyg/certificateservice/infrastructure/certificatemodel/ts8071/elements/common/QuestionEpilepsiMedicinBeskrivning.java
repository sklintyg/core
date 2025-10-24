package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicin.QUESTION_EPILEPSI_MEDICIN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicin.QUESTION_EPILEPSI_MEDICIN_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionEpilepsiMedicinBeskrivning {

  public static final ElementId QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID = new ElementId(
      "14.6");
  public static final FieldId QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_FIELD_ID = new FieldId(
      "14.6");

  private QuestionEpilepsiMedicinBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionEpilepsiMedicinBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_FIELD_ID)
                .name("Ange vilket läkemedel")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_EPILEPSI_MEDICIN_ID,
                    QUESTION_EPILEPSI_MEDICIN_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID,
                    QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_EPILEPSI_MEDICIN_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_EPILEPSI_ID, null)
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
