package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryArbetsformaga {

  public static final ElementId CATEGORY_ARBETSFORMAGA_ID = new ElementId("KAT_4");

  private CategoryArbetsformaga() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryArbetsformaga(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ARBETSFORMAGA_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Arbetsförmåga")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
