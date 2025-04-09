package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryBehandling {

  private static final ElementId BEHANDLING_CATEGORY_ID = new ElementId("KAT_5");

  private CategoryBehandling() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryBehandling(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(BEHANDLING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Behandling")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}