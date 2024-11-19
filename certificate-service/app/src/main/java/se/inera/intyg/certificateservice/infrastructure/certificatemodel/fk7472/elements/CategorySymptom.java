package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySymptom {

  public static final ElementId QUESTION_SYMPTOM_CATEGORY_ID = new ElementId("KAT_1");

  private CategorySymptom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySymptom(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SYMPTOM_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Barnets diagnos eller symtom")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
