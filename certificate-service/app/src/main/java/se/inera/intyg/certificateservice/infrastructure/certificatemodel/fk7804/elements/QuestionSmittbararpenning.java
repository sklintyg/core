package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

public class QuestionSmittbararpenning {

  public static final ElementId QUESTION_SMITTBARARPENNING_ID = new ElementId("27");
  public static final FieldId QUESTION_SMITTBARARPENNING_FIELD_ID = new FieldId("27.1");

  private QuestionSmittbararpenning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSmittbararpenning(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SMITTBARARPENNING_ID)
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(QUESTION_SMITTBARARPENNING_FIELD_ID)
                .label("Förhållningsregler enligt smittskyddslagen på grund av smitta")
                .selectedText("Ja")
                .unselectedText("Ej angivet")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationBoolean.builder()
                .checkboxTrue(
                    new PdfFieldId("form1[0].#subform[0].ksr_AvstangningSmittskyddslagen[0]"))
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
