package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll;

public class QuestionIdentitet {

  public static final ElementId QUESTION_IDENTITET_ID = new ElementId(
      "3");
  public static final FieldId QUESTION_IDENTITET_FIELD_ID = new FieldId(
      "3.1");

  private QuestionIdentitet() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionIdentitet(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        getRadioCode(CodeSystemKvIdKontroll.IDK1),
        getRadioCode(CodeSystemKvIdKontroll.IDK2),
        getRadioCode(CodeSystemKvIdKontroll.IDK3),
        getRadioCode(CodeSystemKvIdKontroll.IDK4),
        getRadioCode(CodeSystemKvIdKontroll.IDK5),
        getRadioCode(CodeSystemKvIdKontroll.IDK6)
    );

    return ElementSpecification.builder()
        .id(QUESTION_IDENTITET_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_IDENTITET_FIELD_ID)
                .name("Identitet är styrkt genom")
                .elementLayout(ElementLayout.COLUMNS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_IDENTITET_ID,
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

  private static ElementConfigurationCode getRadioCode(Code code) {
    return new ElementConfigurationCode(
        new FieldId(code.code()),
        code.displayName(),
        code
    );
  }
}
