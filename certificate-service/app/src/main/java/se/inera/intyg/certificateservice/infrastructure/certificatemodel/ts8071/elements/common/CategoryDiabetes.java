package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryDiabetes {

  private static final ElementId INTYGET_DIABETES_CATEGORY_ID = new ElementId(
      "KAT_6");

  private CategoryDiabetes() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryDiabetes(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_DIABETES_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Diabetes")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
