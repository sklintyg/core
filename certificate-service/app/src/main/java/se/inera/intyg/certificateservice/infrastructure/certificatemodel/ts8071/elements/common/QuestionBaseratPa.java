package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg;

public class QuestionBaseratPa {

  public static final ElementId QUESTION_BASERAT_PA_ID = new ElementId(
      "2");
  public static final FieldId QUESTION_BASERAT_PA_FIELD_ID = new FieldId(
      "2.1");

  private QuestionBaseratPa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBaseratPa(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        CodeFactory.elementConfigurationCode(
            CodeSystemKvInformationskallaForIntyg.JOURNALUPPGIFTER),
        CodeFactory.elementConfigurationCode(CodeSystemKvInformationskallaForIntyg.DISTANSKONTAKT),
        CodeFactory.elementConfigurationCode(CodeSystemKvInformationskallaForIntyg.UNDERSOKNING)
    );

    return ElementSpecification.builder()
        .id(QUESTION_BASERAT_PA_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_BASERAT_PA_FIELD_ID)
                .name("Intyget är baserat på")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_BASERAT_PA_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}