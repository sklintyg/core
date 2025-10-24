package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorning.QUESTION_MEDVETANDESTORNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorning.QUESTION_MEDVETANDESTORNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMedvetandestorningTidpunkt {

  public static final ElementId QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID = new ElementId(
      "14.9");
  public static final FieldId QUESTION_MEDVETANDESTORNING_TIDPUNKT_FIELD_ID = new FieldId(
      "14.9");

  private QuestionMedvetandestorningTidpunkt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedvetandestorningTidpunkt() {
    return ElementSpecification.builder()
        .id(QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_MEDVETANDESTORNING_TIDPUNKT_FIELD_ID)
                .name("Ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MEDVETANDESTORNING_ID,
                    QUESTION_MEDVETANDESTORNING_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID,
                    QUESTION_MEDVETANDESTORNING_TIDPUNKT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_MEDVETANDESTORNING_ID)
        )
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
