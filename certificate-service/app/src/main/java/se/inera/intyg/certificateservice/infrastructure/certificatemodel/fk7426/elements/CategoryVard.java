package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

import java.util.List;

public class CategoryVard {

  private static final ElementId VARD_CATEGORY_ID = new ElementId("KAT_7");

  private CategoryVard() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryVard(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(VARD_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Sjukhusvård eller hemsjukvård")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}