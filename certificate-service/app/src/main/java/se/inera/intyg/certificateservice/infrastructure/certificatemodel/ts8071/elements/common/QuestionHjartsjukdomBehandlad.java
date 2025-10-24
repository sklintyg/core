package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionHjartsjukdomBehandlad {

  public static final ElementId QUESTION_HJARTSJUKDOM_BEHANDLAD_ID = new ElementId("11.3");
  public static final FieldId QUESTION_HJARTSJUKDOM_BEHANDLAD_FIELD_ID = new FieldId("11.3");

  private QuestionHjartsjukdomBehandlad() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHjartsjukdomBehandlad(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HJARTSJUKDOM_BEHANDLAD_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HJARTSJUKDOM_BEHANDLAD_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Är tillståndet behandlat?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_ID,
                    QUESTION_HJARTSJUKDOM_BEHANDLAD_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_HJARTSJUKDOM_ID,
                    QUESTION_HJARTSJUKDOM_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_HJARTSJUKDOM_ID, null))
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_HJARTSJUKDOM_ID))
        .children(List.of(children))
        .build();
  }
}