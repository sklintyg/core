package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionKontakt {

  public static final ElementId QUESTION_KONTAKT_ID = new ElementId(
      "103");
  public static final FieldId QUESTION_KONTAKT_FIELD_ID = new FieldId(
      "103.1");

  private QuestionKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKontakt(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_KONTAKT_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(QUESTION_KONTAKT_FIELD_ID)
                .name("Kontakt med arbetsgivaren")
                .label(
                    "Jag önskar att arbetsgivaren kontaktar vårdenheten. Patienten har lämnat samtycke för kontakt mellan arbetsgivare och vårdgivare.")
                .selectedText("Ja")
                .unselectedText("Ej angivet")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
                )
            ))
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false))
        .children(List.of(children))
        .build();
  }

}