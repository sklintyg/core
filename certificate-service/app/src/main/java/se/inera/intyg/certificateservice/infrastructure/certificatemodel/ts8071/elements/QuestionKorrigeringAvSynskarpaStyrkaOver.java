package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionKorrigeringAvSynskarpa.QUESTION_KORRIGERING_AV_SYNSKARPA_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa;

public class QuestionKorrigeringAvSynskarpaStyrkaOver {

  public static final ElementId QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_ID = new ElementId(
      "6.4");
  public static final FieldId QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_FIELD_ID = new FieldId(
      "6.4");

  private QuestionKorrigeringAvSynskarpaStyrkaOver() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKorrigeringAvSynskarpaStyrkaOver() {
    return ElementSpecification.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Glasögon, något av glasen har en styrka över plus 8 dioptrier. Tolereras korrektionen väl?"
                )
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
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_ID,
                    QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    new FieldId(
                        CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()
                    )
                )
            )
        )
        .shouldValidate(
            ShouldValidateFactory.codeList(QUESTION_KORRIGERING_AV_SYNSKARPA_ID, List.of(
                new FieldId(
                    CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code())))
        )
        .mapping(
            new ElementMapping(QUESTION_KORRIGERING_AV_SYNSKARPA_ID, null)
        )
        .build();
  }
}