package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMedicineringV2 {

  public static final ElementId QUESTION_MEDICINERING_V2_ID = new ElementId("21");
  public static final FieldId QUESTION_MEDICINERING_V2_FIELD_ID = new FieldId("21.1");

  private QuestionMedicineringV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicineringV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINERING_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MEDICINERING_V2_FIELD_ID)
                .name(
                    "Har personen någon stadigvarande medicinering som inte nämnts i något avsnitt ovan?")
                .selectedText("Ja")
                .unselectedText("Nej")
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
                    QUESTION_MEDICINERING_V2_ID,
                    QUESTION_MEDICINERING_V2_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}

