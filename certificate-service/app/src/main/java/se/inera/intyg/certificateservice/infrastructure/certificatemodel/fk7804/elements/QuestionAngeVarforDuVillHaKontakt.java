package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionKontakt.QUESTION_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionKontakt.QUESTION_KONTAKT_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionAngeVarforDuVillHaKontakt {

  public static final ElementId QUESTION_VARFOR_KONTAKT_ID = new ElementId(
      "26.2");
  public static final FieldId QUESTION_VARFOR_KONTAKT_FIELD_ID = new FieldId(
      "26.2");

  private QuestionAngeVarforDuVillHaKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAngeVarforDuVillHaKontakt() {
    return ElementSpecification.builder()
        .id(QUESTION_VARFOR_KONTAKT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_VARFOR_KONTAKT_FIELD_ID)
                .name("Jag önskar att Försäkringskassan kontaktar mig")
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
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            ShouldValidateFactory.checkboxBoolean(QUESTION_KONTAKT_ID, true)
        )
        .build();
  }

}
