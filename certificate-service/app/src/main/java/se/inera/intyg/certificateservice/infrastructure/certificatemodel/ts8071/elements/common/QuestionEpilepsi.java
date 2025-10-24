package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionEpilepsi {

  public static final ElementId QUESTION_EPILEPSI_ID = new ElementId("14");
  public static final FieldId QUESTION_EPILEPSI_FIELD_ID = new FieldId("14.1");

  private QuestionEpilepsi() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionEpilepsi(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_EPILEPSI_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_EPILEPSI_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har eller har personen haft epilepsi?")
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
                    QUESTION_EPILEPSI_ID,
                    QUESTION_EPILEPSI_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}