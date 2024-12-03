package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySynskarpa {

  private static final ElementId INTYGET_SYNSKARPA_CATEGORY_ID = new ElementId("KAT_1.1");

  private CategorySynskarpa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySynskarpa(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_SYNSKARPA_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Synskärpa")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
