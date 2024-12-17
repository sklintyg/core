package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorringerAvSynskarpa;

public class QuestionKorrigeringAvSynskarpa {

  public static final ElementId QUESTION_KORRIGERING_AV_SYNSKARPA_ID = new ElementId("6");
  public static final FieldId QUESTION_KORRIGERING_AV_SYNSKARPA_FIELD_ID = new FieldId("6.1");

  private QuestionKorrigeringAvSynskarpa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKorrigeringAvSynskarpa() {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(
            CodeSystemKorringerAvSynskarpa.GLASOGON_UTAN_STYRKA_OVER_8_DIOPTRIER),
        CodeFactory.elementConfigurationCode(
            CodeSystemKorringerAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER),
        CodeFactory.elementConfigurationCode(CodeSystemKorringerAvSynskarpa.KONTAKTLINSER)
    );

    return ElementSpecification.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_KORRIGERING_AV_SYNSKARPA_FIELD_ID)
                .name("Korrigering av synskärpa genom")
                .elementLayout(ElementLayout.COLUMNS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    List.of(
                        new FieldId(
                            CodeSystemKorringerAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()),
                        new FieldId(
                            CodeSystemKorringerAvSynskarpa.GLASOGON_UTAN_STYRKA_OVER_8_DIOPTRIER.code()),
                        new FieldId(CodeSystemKorringerAvSynskarpa.KONTAKTLINSER.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    List.of(new FieldId(
                        CodeSystemKorringerAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code())),
                    List.of(
                        new FieldId(
                            CodeSystemKorringerAvSynskarpa.GLASOGON_UTAN_STYRKA_OVER_8_DIOPTRIER.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    List.of(new FieldId(
                        CodeSystemKorringerAvSynskarpa.GLASOGON_UTAN_STYRKA_OVER_8_DIOPTRIER.code())),
                    List.of(
                        new FieldId(
                            CodeSystemKorringerAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code())
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}