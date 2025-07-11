package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0010;

public class QuestionKannedomOmPatienten {

  public static final ElementId QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID = new ElementId("2");
  private static final FieldId QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_FIELD_ID = new FieldId(
      "2.2");


  private QuestionKannedomOmPatienten() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKannedomOmPatienten(
      ElementSpecification... children) {
    final var dropdownItems = List.of(
        new ElementConfigurationCode(
            new FieldId(""),
            "Välj i listan",
            null
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0010.INGEN_TIDIGARE.code()),
            CodeSystemKvFkmu0010.INGEN_TIDIGARE.displayName(),
            CodeSystemKvFkmu0010.INGEN_TIDIGARE
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.code()),
            CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.displayName(),
            CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0010.MER_AN_ETT_AR.code()),
            CodeSystemKvFkmu0010.MER_AN_ETT_AR.displayName(),
            CodeSystemKvFkmu0010.MER_AN_ETT_AR
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID)
        .configuration(
            ElementConfigurationDropdownCode.builder()
                .id(QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_FIELD_ID)
                .name("Jag har kännedom om patienten sedan")
                .list(dropdownItems)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID,
                    dropdownItems.stream().skip(1).map(ElementConfigurationCode::id).toList()
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
