package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySjukvardandeInsats {

  private static final ElementId SJUKVARDANDE_INSATS_CATEGORY_ID = new ElementId("KAT_8");

  private CategorySjukvardandeInsats() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySjukvardandeInsats(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(SJUKVARDANDE_INSATS_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Sjukvårdande insatser inom personlig assistans")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
