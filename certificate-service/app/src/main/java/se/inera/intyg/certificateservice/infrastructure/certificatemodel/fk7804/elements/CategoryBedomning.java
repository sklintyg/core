package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryBedomning {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_8");

  private CategoryBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryBedomning(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Bedömning")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}

