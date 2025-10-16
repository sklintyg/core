package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontakt.QUESTION_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontakt.QUESTION_KONTAKT_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionKontaktBeskrivning {

  public static final ElementId QUESTION_KONTAKT_BESKRIVNING_ID = new ElementId("9.2");
  public static final FieldId QUESTION_KONTAKT_BESKRIVNING_FIELD_ID = new FieldId("9.2");

  private QuestionKontaktBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKontaktBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_KONTAKT_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_KONTAKT_BESKRIVNING_FIELD_ID)
                .name(
                    "Ange varför du vill ha kontakt och vem som i första hand ska kontaktas samt kontaktuppgifter")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_KONTAKT_ID,
                    QUESTION_KONTAKT_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_KONTAKT_BESKRIVNING_ID,
                    QUESTION_KONTAKT_BESKRIVNING_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(QUESTION_KONTAKT_ID, null)
        )
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_KONTAKT_ID, true)
        )
        .build();
  }
}
