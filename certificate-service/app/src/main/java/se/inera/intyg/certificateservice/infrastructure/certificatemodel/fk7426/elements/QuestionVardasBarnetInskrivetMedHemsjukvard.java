package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVardasBarnetInskrivetMedHemsjukvard {

  public static final ElementId QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID = new ElementId(
      "62.3");
  public static final FieldId QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID = new FieldId(
      "62.3");

  private QuestionVardasBarnetInskrivetMedHemsjukvard() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionVardasBarnetInskrivetMedHemsjukvard(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Är barnet inskrivet med hemsjukvård?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID,
                    QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID, null))
        .children(List.of(children))
        .build();
  }
}
