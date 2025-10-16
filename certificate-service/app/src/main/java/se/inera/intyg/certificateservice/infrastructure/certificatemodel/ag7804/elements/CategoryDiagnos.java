package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryDiagnos {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_4");

  private CategoryDiagnos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryDiagnos(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Diagnos")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
