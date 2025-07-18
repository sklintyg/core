package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryTestQuestions {

  private static final ElementId TEST_CATEGORY_ID = new ElementId("KAT_1");

  private CategoryTestQuestions() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryTestQuestions(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(TEST_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Följande komponenter finns i Certificate Service")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}