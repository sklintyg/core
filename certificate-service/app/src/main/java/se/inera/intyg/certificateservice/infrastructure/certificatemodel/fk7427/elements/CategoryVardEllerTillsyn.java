package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryVardEllerTillsyn {

  private static final ElementId VARD_ELLER_TILLSYN_CATEGORY_ID = new ElementId("KAT_4");

  private CategoryVardEllerTillsyn() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryVardEllerTillsyn(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(VARD_ELLER_TILLSYN_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Vård eller tillsyn")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
