package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykisk.QUESTION_PSYKISK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykisk.QUESTION_PSYKISK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionPsykiskTidpunkt {

  public static final ElementId QUESTION_PSYKISK_TIDPUNKT_ID = new ElementId(
      "19.3");
  public static final FieldId QUESTION_PSYKISK_TIDPUNKT_FIELD_ID = new FieldId(
      "19.3");

  private QuestionPsykiskTidpunkt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskTidpunkt() {
    return ElementSpecification.builder()
        .id(QUESTION_PSYKISK_TIDPUNKT_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_PSYKISK_TIDPUNKT_FIELD_ID)
                .name("När hade personen senast läkarkontakt med anledning av sin diagnos?")
                .description("Ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_PSYKISK_ID,
                    QUESTION_PSYKISK_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PSYKISK_TIDPUNKT_ID,
                    QUESTION_PSYKISK_TIDPUNKT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PSYKISK_TIDPUNKT_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_PSYKISK_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_PSYKISK_ID, null)
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
