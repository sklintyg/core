package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryBedomning {

  private static final ElementId INTYGET_BEDOMNING_CATEGORY_ID = new ElementId(
      "KAT_17");

  private CategoryBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryBedomning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_BEDOMNING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Bedömning")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
