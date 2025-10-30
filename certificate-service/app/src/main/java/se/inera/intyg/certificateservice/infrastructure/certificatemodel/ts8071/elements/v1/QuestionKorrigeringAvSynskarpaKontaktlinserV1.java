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

public class QuestionKorrigeringAvSynskarpaKontaktlinserV1 {

  public static final ElementId QUESTION_KONTAKTLINSER_V1_ID = new ElementId(
      "6.6");
  public static final FieldId QUESTION_KONTAKTLINSER_V1_FIELD_ID = new FieldId(
      "6.6");

  private QuestionKorrigeringAvSynskarpaKontaktlinserV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKorrigeringAvSynskarpaKontaktlinserV1() {
    return ElementSpecification.builder()
        .id(QUESTION_KONTAKTLINSER_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_KONTAKTLINSER_V1_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Kontaktlinser. Tolereras korrektionen väl?")
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
                    QUESTION_KONTAKTLINSER_V1_ID,
                    QUESTION_KONTAKTLINSER_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID,
                    new FieldId(
                        CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER.code()
                    )
                )
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.codeList(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID, List.of(
                new FieldId(
                    CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER.code())))
        )
        .mapping(
            new ElementMapping(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID, null)
        )
        .build();
  }
}