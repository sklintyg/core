package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionBalanssinne {

  public static final ElementId QUESTION_BALANSSINNE_ID = new ElementId("8");
  public static final FieldId QUESTION_BALANSSINNE_FIELD_ID = new FieldId("8.1");

  private QuestionBalanssinne() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBalanssinne(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_BALANSSINNE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_BALANSSINNE_FIELD_ID)
                .description(
                    "Här avses överraskande anfall av balansrubbningar eller yrsel som inträffat nyligen och krävt läkarkontakt, exempelvis vid sjukdomen Morbus Menière. Balansrubbningar eller yrsel som beror på till exempel godartad lägesyrsel (kristallsjuka), lågt blodtryck eller migrän "
                        + "behöver inte anges.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen överraskande anfall av balansrubbningar eller yrsel som kan innebära en trafiksäkerhetsrisk?")
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
                    QUESTION_BALANSSINNE_ID,
                    QUESTION_BALANSSINNE_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}