package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryGrund {

  public static final ElementId QUESTION_GRUND_CATEGORY_ID = new ElementId("KAT_1");

  private CategoryGrund() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryGrund(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_GRUND_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Grund för medicinskt underlag")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}