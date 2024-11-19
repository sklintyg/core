package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySamtycke {

  private static final ElementId QUESTION_SAMTYCKE_CATEGORY_ID = new ElementId("KAT_3");

  private CategorySamtycke() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySamtycke(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SAMTYCKE_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Samtycke för närståendes stöd")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
