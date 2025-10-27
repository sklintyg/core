package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskLakemedelV1.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskLakemedelV1.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskV1.QUESTION_NEUROPSYKIATRISK_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionNeuropsykiatriskLakemedelBeskrivningV1 {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_V1_ID = new ElementId(
      "20.5");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_FIELD_V1_ID = new FieldId(
      "20.5");

  private QuestionNeuropsykiatriskLakemedelBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatriskLakemedelBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_FIELD_V1_ID)
                .name("Ange vilket/vilka läkemedel")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID,
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_V1_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_V1_ID,
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_FIELD_V1_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_V1_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROPSYKIATRISK_V1_ID, null)
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
