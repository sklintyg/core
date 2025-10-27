package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskV1.QUESTION_PSYKISK_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskV1.QUESTION_PSYKISK_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionPsykiskTidpunktV1 {

  public static final ElementId QUESTION_PSYKISK_TIDPUNKT_V1_ID = new ElementId(
      "19.3");
  public static final FieldId QUESTION_PSYKISK_TIDPUNKT_FIELD_V1_ID = new FieldId(
      "19.3");

  private QuestionPsykiskTidpunktV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskTidpunktV1() {
    return ElementSpecification.builder()
        .id(QUESTION_PSYKISK_TIDPUNKT_V1_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_PSYKISK_TIDPUNKT_FIELD_V1_ID)
                .name("När hade personen senast läkarkontakt med anledning av sin diagnos?")
                .label("Ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_PSYKISK_V1_ID,
                    QUESTION_PSYKISK_FIELD_V1_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PSYKISK_TIDPUNKT_V1_ID,
                    QUESTION_PSYKISK_TIDPUNKT_FIELD_V1_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PSYKISK_TIDPUNKT_V1_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_PSYKISK_V1_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_PSYKISK_V1_ID, null)
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
