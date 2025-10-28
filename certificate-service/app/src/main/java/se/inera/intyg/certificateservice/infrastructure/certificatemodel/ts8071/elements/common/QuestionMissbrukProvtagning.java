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

public class QuestionMissbrukProvtagning {

  public static final ElementId QUESTION_MISSBRUK_PROVTAGNING_ID = new ElementId(
      "18.5");
  public static final FieldId QUESTION_MISSBRUK_PROVTAGNING_FIELD_ID = new FieldId(
      "18.5");

  private QuestionMissbrukProvtagning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukProvtagning(ElementId parentElementId,
      FieldId parentFieldId, ElementId mappingElementId) {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_PROVTAGNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_MISSBRUK_PROVTAGNING_FIELD_ID)
                .name(
                    "Om provtagning gjorts ska resultatet redovisas nedan. Ange datum för provtagning och resultat.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    parentElementId,
                    parentFieldId
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MISSBRUK_PROVTAGNING_ID,
                    QUESTION_MISSBRUK_PROVTAGNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MISSBRUK_PROVTAGNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(parentElementId)
        )
        .mapping(
            new ElementMapping(mappingElementId, null)
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
