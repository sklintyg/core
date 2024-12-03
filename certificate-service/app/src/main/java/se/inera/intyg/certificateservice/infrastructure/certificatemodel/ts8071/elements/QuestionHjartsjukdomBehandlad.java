package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionHjartsjukdomBehandlad {

  public static final ElementId QUESTION_HJARTSJUKDOM_BEHANDLAD_ID = new ElementId("11.2");
  public static final FieldId QUESTION_HJARTSJUKDOM_BEHANDLAD_FIELD_ID = new FieldId("11.2");

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
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
