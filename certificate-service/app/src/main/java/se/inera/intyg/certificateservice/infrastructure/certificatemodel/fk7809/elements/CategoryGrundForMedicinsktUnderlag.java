package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryGrundForMedicinsktUnderlag {

  private static final ElementId GRUND_FOR_MEDICINSKT_UNDERLAG_CATEGORY_ID = new ElementId("KAT_1");

  private CategoryGrundForMedicinsktUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryGrundForMedicinsktUnderlag(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(GRUND_FOR_MEDICINSKT_UNDERLAG_CATEGORY_ID)
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
