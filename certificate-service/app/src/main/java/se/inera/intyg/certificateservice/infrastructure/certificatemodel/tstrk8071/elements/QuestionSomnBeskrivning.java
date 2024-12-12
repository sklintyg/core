package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSomn.QUESTION_SOMN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSomn.QUESTION_SOMN_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionSomnBeskrivning {

  public static final ElementId QUESTION_SOMN_BESKRIVNING_ID = new ElementId(
      "17.2");
  public static final FieldId QUESTION_SOMN_BESKRIVNING_FIELD_ID = new FieldId(
      "17.2");

  private QuestionSomnBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSomnBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_SOMN_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_SOMN_BESKRIVNING_FIELD_ID)
                .name("Ange vilken diagnos/vilka symtom")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SOMN_ID,
                    QUESTION_SOMN_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SOMN_BESKRIVNING_ID,
                    QUESTION_SOMN_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SOMN_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_SOMN_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_SOMN_ID, null)
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
