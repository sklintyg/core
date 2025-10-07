package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSysselsattning {

  public static final ElementId QUESTION_SYSSELSATTNING_ID = new ElementId("1");
  public static final FieldId QUESTION_SYSSELSATTNING_FIELD_ID = new FieldId("1.1");

  private QuestionSysselsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSysselsattning() {
    return ElementSpecification.builder()
        .id(QUESTION_SYSSELSATTNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_SYSSELSATTNING_FIELD_ID)
                .name(
                    "Bedöm arbetsförmåga utifrån ordinarie arbete. Ange yrke och arbetsuppgifter.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SYSSELSATTNING_ID,
                    QUESTION_SYSSELSATTNING_FIELD_ID
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
        .build();
  }
}
