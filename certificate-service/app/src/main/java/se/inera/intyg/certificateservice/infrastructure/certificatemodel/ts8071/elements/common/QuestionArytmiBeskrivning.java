package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmi.QUESTION_ARYTMI_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmi.QUESTION_ARYTMI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionArytmiBeskrivning {

  public static final ElementId QUESTION_ARYTMI_BESKRIVNING_ID = new ElementId(
      "11.6");
  public static final FieldId QUESTION_ARYTMI_BESKRIVNING_FIELD_ID = new FieldId(
      "11.6");

  private QuestionArytmiBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionArytmiBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_ARYTMI_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_ARYTMI_BESKRIVNING_FIELD_ID)
                .name("Ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_ARYTMI_ID,
                    QUESTION_ARYTMI_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_ARYTMI_BESKRIVNING_ID,
                    QUESTION_ARYTMI_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_ARYTMI_BESKRIVNING_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_ARYTMI_ID)
        )
        .mapping(new ElementMapping(QUESTION_HJARTSJUKDOM_ID, null))
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
