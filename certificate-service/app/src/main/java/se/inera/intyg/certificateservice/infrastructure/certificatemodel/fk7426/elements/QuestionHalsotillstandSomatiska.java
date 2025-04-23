package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.*;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

import java.util.List;

public class QuestionHalsotillstandSomatiska {

  public static final ElementId QUESTION_HALSOTILLSTAND_SOMATISKA_ID = new ElementId("59.1");
  public static final ElementId QUESTION_HALSOTILLSTAND_SOMATISKA_PARENT_ID = new ElementId("59");
  public static final FieldId QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID = new FieldId("59.1");
  private static final short LIMIT = 4000;

  private QuestionHalsotillstandSomatiska() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHalsotillstandSomatiska() {
    return ElementSpecification.builder()
        .id(QUESTION_HALSOTILLSTAND_SOMATISKA_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Barnets aktuella somatiska hälsotillstånd")
                .description(
                    "Beskriv barnets nuvarande somatiska hälsotillstånd. Ta med aktuella undersökningsfynd, testresultat och observationer som har betydelse för din bedömning av allvarligt sjukt barn. Om läkarutlåtandet avser misstanke, beskriv på vilket sätt undersökningsfynden innebär en konkret misstanke om ett specifikt sjukdomstillstånd.")
                .id(QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_HALSOTILLSTAND_SOMATISKA_ID, LIMIT)
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

