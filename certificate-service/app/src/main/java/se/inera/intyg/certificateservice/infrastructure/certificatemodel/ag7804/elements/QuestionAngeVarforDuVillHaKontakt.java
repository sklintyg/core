package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionKontakt.QUESTION_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionKontakt.QUESTION_KONTAKT_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionAngeVarforDuVillHaKontakt {

  public static final ElementId QUESTION_VARFOR_KONTAKT_ID = new ElementId(
      "103.2");
  public static final FieldId QUESTION_VARFOR_KONTAKT_FIELD_ID = new FieldId(
      "103.2");

  private QuestionAngeVarforDuVillHaKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAngeVarforDuVillHaKontakt() {
    return ElementSpecification.builder()
        .id(QUESTION_VARFOR_KONTAKT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_VARFOR_KONTAKT_FIELD_ID)
                .name(
                    "Ange varför du vill ha kontakt och vem som i första hand ska kontaktas samt kontaktuppgifter")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_KONTAKT_ID,
                    QUESTION_KONTAKT_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_KONTAKT_ID, true)
        )
        .includeWhenRenewing(false)
        .mapping(new ElementMapping(QUESTION_KONTAKT_ID, null))
        .build();
  }

}