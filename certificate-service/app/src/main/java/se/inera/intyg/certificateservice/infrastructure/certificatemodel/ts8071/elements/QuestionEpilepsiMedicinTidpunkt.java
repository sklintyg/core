package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiMedicin.QUESTION_EPILEPSI_MEDICIN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiMedicin.QUESTION_EPILEPSI_MEDICIN_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionEpilepsiMedicinTidpunkt {

  public static final ElementId QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_ID = new ElementId(
      "14.7");
  public static final FieldId QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_FIELD_ID = new FieldId(
      "14.7");

  private QuestionEpilepsiMedicinTidpunkt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionEpilepsiMedicinTidpunkt() {
    return ElementSpecification.builder()
        .id(QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_FIELD_ID)
                .name("Om läkemedelsbehandling avslutats, ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_EPILEPSI_MEDICIN_ID,
                    QUESTION_EPILEPSI_MEDICIN_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_ID,
                    QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_EPILEPSI_MEDICIN_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_EPILEPSI_MEDICIN_ID, null)
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
