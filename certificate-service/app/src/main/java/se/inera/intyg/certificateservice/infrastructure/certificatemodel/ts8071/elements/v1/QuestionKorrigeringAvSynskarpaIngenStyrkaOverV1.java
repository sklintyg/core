package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaV1.QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa;

public class QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1 {

  public static final ElementId QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID = new ElementId(
      "6.2");
  public static final FieldId QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_FIELD_ID = new FieldId(
      "6.2");

  private QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKorrigeringAvSynskarpaIngenStyrkaOverV1() {
    return ElementSpecification.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Glasögon, inget av glasen har en styrka över plus 8 dioptrier. Tolereras korrektionen väl?"
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
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID,
                    QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID,
                    new FieldId(
                        CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code()
                    )
                )
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.codeList(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID, List.of(
                new FieldId(
                    CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code())))
        )
        .mapping(
            new ElementMapping(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID, null)
        )
        .build();
  }
}