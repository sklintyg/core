package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAtgarderSomKanFramjaAttergang {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_10");

  private CategoryAtgarderSomKanFramjaAttergang() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAtgarderSomKanFramjaAttergang(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Åtgärder som kan främja återgången i arbete")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}

