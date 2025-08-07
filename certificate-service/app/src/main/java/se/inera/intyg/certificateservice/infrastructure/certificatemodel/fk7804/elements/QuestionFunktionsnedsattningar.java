package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class QuestionFunktionsnedsattningar {

  public static final ElementId QUESTION_FUNKTIONSNEDSATTNINGAR_ID = new ElementId(
      "35");
  private static final FieldId QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID = new FieldId("35.1");

  private QuestionFunktionsnedsattningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionFunktionsnedsattningar() {
    return ElementSpecification.builder()
        .id(QUESTION_FUNKTIONSNEDSATTNINGAR_ID)
        .configuration(
            ElementConfigurationIcf.builder()
                .id(QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID)
                .name("Ska ha ICF-stöd")
                .build()
        )
        .build();
  }

}
