package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMedicinering {

  public static final ElementId QUESTION_MEDICINERING_ID = new ElementId("21");
  public static final FieldId QUESTION_MEDICINERING_FIELD_ID = new FieldId("21.1");

  private QuestionMedicinering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicinering(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINERING_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MEDICINERING_FIELD_ID)
                .name(
                    "Har personen någon stadigvarande medicinering som inte nämnts i något avsnitt ovan?")
                .description(
                    "Du behöver inte ange p-piller, vitamintillskott eller behandling mot hudsjukdomar. Inte heller medicin mot allergi, astma, mage/tarm exempelvis magkatarr, gikt, KOL, förstorad prostata, impotens eller nedsatt funktion i sköldkörteln.")
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
                    QUESTION_MEDICINERING_ID,
                    QUESTION_MEDICINERING_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}