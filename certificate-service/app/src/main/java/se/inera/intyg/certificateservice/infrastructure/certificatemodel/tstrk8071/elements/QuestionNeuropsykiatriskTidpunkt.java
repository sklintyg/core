package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatrisk.QUESTION_NEUROPSYKIATRISK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatrisk.QUESTION_NEUROPSYKIATRISK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionNeuropsykiatriskTidpunkt {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_TIDPUNKT_ID = new ElementId(
      "20.3");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_TIDPUNKT_FIELD_ID = new FieldId(
      "20.3");

  private QuestionNeuropsykiatriskTidpunkt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatriskTidpunkt() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_TIDPUNKT_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_NEUROPSYKIATRISK_TIDPUNKT_FIELD_ID)
                .name("När hade personen senast läkarkontakt med anledning av sin diagnos?")
                .label("Ange tidpunkt")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROPSYKIATRISK_ID,
                    QUESTION_NEUROPSYKIATRISK_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROPSYKIATRISK_TIDPUNKT_ID,
                    QUESTION_NEUROPSYKIATRISK_TIDPUNKT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NEUROPSYKIATRISK_TIDPUNKT_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_NEUROPSYKIATRISK_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROPSYKIATRISK_ID, null)
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
