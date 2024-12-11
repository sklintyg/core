package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionLakemedel.QUESTION_LAKEMEDEL_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionLakemedel.QUESTION_LAKEMEDEL_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionLakemedelBeskrivning {

  public static final ElementId QUESTION_LAKEMEDEL_BESKRIVNING_ID = new ElementId(
      "18.9");
  public static final FieldId QUESTION_LAKEMEDEL_BESKRIVNING_FIELD_ID = new FieldId(
      "18.9");

  private QuestionLakemedelBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionLakemedelBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_LAKEMEDEL_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_LAKEMEDEL_BESKRIVNING_FIELD_ID)
                .name("Ange läkemedel och ordinerad dos")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_LAKEMEDEL_ID,
                    QUESTION_LAKEMEDEL_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_LAKEMEDEL_BESKRIVNING_ID,
                    QUESTION_LAKEMEDEL_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_LAKEMEDEL_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_LAKEMEDEL_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_LAKEMEDEL_ID, null)
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
