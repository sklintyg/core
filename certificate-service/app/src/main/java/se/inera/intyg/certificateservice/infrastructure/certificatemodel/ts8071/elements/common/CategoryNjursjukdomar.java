package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryNjursjukdomar {

  private static final ElementId INTYGET_NJURSJUKDOM_CATEGORY_ID = new ElementId(
      "KAT_9");

  private CategoryNjursjukdomar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryNjursjukdomar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_NJURSJUKDOM_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Allvarlig nedsatt njurfunktion och njursjukdomar")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
