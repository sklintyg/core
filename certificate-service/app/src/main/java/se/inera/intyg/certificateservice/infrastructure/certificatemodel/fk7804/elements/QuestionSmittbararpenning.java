package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class QuestionSmittbararpenning {

  public static final ElementId QUESTION_SMITTBARARPENNING_ID = new ElementId("27");
  public static final FieldId QUESTION_SMITTBARARPENNING_FIELD_ID = new FieldId("27.1");

  private QuestionSmittbararpenning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSmittbararpenning(
      ElementSpecification... children) {
    return null;
  }
}

