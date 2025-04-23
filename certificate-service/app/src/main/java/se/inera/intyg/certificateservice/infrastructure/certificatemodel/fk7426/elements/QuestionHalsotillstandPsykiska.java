package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.*;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

import java.util.List;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_PARENT_ID;

public class QuestionHalsotillstandPsykiska {

  public static final ElementId QUESTION_HALSOTILLSTAND_PSYKISKA_ID = new ElementId("59.2");
  public static final FieldId QUESTION_HALSOTILLSTAND_PSYKISKA_FIELD_ID = new FieldId("59.2");
  private static final short LIMIT = 4000;

  private QuestionHalsotillstandPsykiska() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHalsotillstandPsykiska() {
    return ElementSpecification.builder()
        .id(QUESTION_HALSOTILLSTAND_PSYKISKA_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Barnets aktuella psykiska hälsotillstånd")
                .description(
                    "Beskriv barnets nuvarande psykiska påverkan. Ta med aktuella undersökningsfynd, testresultat och observationer som har betydelse för din bedömning av allvarligt sjukt barn.")
                .id(QUESTION_HALSOTILLSTAND_PSYKISKA_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_HALSOTILLSTAND_PSYKISKA_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        ).mapping(new ElementMapping(QUESTION_HALSOTILLSTAND_SOMATISKA_PARENT_ID, null))
        .build();
  }
}

