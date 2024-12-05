package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryIdentitet {

  private static final ElementId IDENTITET_CATEGORY_ID = new ElementId(
      "KAT_0.2"); // TODO: Add description

  private CategoryIdentitet() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryIdentitet(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(IDENTITET_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Identitet")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
