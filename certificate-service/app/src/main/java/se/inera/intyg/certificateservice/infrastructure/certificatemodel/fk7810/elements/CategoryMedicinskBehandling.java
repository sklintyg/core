package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryMedicinskBehandling {

  private static final ElementId MEDICINSK_BEHANDLING_CATEGORY_ID = new ElementId("KAT_6");

  private CategoryMedicinskBehandling() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryMedicinskBehandling(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(MEDICINSK_BEHANDLING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Medicinska behandlingar")
                .description(
                    "Ange pågående och planerade medicinska behandlingar eller åtgärder som är relevanta utifrån funktionsnedsättningen. Det kan vara ordinerade läkemedel, hjälpmedel (inklusive medicinteknisk utrustning), träningsinsatser eller särskild kost. Ange den medicinska indikationen och syftet med behandlingen eller åtgärden. Du kan även beskriva andra behandlingar och åtgärder som prövats utifrån funktionsnedsättningen.")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}