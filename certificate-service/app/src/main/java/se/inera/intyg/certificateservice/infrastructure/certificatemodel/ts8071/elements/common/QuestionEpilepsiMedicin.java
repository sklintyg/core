package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfall.QUESTION_EPILEPSI_ANFALL_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfall.QUESTION_EPILEPSI_ANFALL_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionEpilepsiMedicin {

  public static final ElementId QUESTION_EPILEPSI_MEDICIN_ID = new ElementId("14.5");
  public static final FieldId QUESTION_EPILEPSI_MEDICIN_FIELD_ID = new FieldId("14.5");

  private QuestionEpilepsiMedicin() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionEpilepsiMedicin(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_EPILEPSI_MEDICIN_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_EPILEPSI_MEDICIN_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har eller har personen haft någon krampförebyggande läkemedelsbehandling mot epilepsi?")
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
                    QUESTION_EPILEPSI_MEDICIN_ID,
                    QUESTION_EPILEPSI_MEDICIN_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_EPILEPSI_ID,
                    QUESTION_EPILEPSI_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_EPILEPSI_ANFALL_ID,
                    QUESTION_EPILEPSI_ANFALL_FIELD_ID
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.radioBooleans(
                List.of(QUESTION_EPILEPSI_ID, QUESTION_EPILEPSI_ANFALL_ID)
            )
        )
        .mapping(new ElementMapping(QUESTION_EPILEPSI_ID, null))
        .children(List.of(children))
        .build();
  }
}