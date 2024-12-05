package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatriskLakemedel.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatriskLakemedel.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionNeuropsykiatriskLakemedelBeskrivning {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_ID = new ElementId(
      "20.5");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_FIELD_ID = new FieldId(
      "20.5");

  private QuestionNeuropsykiatriskLakemedelBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatriskLakemedelBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_FIELD_ID)
                .name("Ange vilket/vilka läkemedel")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID,
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_ID,
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID, null)
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
