package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryPeriodSjukdom {

  private static final ElementId PERIOD_CATEGORY_ID = new ElementId("KAT_6");

  private CategoryPeriodSjukdom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryPeriodSjukdom(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(PERIOD_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Period för sjukdom")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}