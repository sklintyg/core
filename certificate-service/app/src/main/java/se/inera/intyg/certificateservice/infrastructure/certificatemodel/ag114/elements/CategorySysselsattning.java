package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySysselsattning {

  public static final ElementId CATEGORY_SYSSELSATTNING_ID = new ElementId("KAT_2");

  private CategorySysselsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySysselsattning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_SYSSELSATTNING_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Sysselsättning")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
