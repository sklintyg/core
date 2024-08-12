package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryPeriod {

  private static final ElementId QUESTION_PERIOD_CATEGORY_ID = new ElementId("KAT_2");

  private CategoryPeriod() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryPeriod(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Period som barnet inte bör vårdas i ordinarie tillsynsform")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
