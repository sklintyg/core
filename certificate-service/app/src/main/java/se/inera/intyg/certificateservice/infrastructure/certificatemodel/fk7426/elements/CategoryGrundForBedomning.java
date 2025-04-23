package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryGrundForBedomning {

  private static final ElementId GRUND_FOR_BEDOMNING_CATEGORY_ID = new ElementId("KAT_4");

  private CategoryGrundForBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryGrundForBedomning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(GRUND_FOR_BEDOMNING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Grund för bedömning")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}