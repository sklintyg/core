package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMedvetandestorning {

  public static final ElementId QUESTION_MEDVETANDESTORNING_ID = new ElementId("14.8");
  public static final FieldId QUESTION_MEDVETANDESTORNING_FIELD_ID = new FieldId("14.8");

  private QuestionMedvetandestorning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedvetandestorning(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MEDVETANDESTORNING_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MEDVETANDESTORNING_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har eller har personen haft någon annan medvetandestörning?")
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
                    QUESTION_MEDVETANDESTORNING_ID,
                    QUESTION_MEDVETANDESTORNING_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_EPILEPSI_ID, null))
        .children(List.of(children))
        .build();
  }
}