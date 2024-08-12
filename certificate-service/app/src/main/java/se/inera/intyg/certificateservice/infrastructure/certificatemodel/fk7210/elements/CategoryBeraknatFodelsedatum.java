package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryBeraknatFodelsedatum {

  public static final ElementId QUESTION_BERAKNAT_FODELSEDATUM_CATEGORY_ID = new ElementId("KAT_1");

  private CategoryBeraknatFodelsedatum() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryBeraknatFodelsedatum(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_BERAKNAT_FODELSEDATUM_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Beräknat födelsedatum")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
