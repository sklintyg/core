package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryMedicinskBehandling {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_7");

  private CategoryMedicinskBehandling() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryMedicinskBehandling(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Medicinsk behandling")
                .description(
                    """
                        Här beskriver du de medicinska behandlingar/åtgärder som kan påverka arbetsförmågan, vad de förväntas leda till, och en (preliminär) tidplan för åtgärderna.
                        
                        Om olika åtgärder behöver ske i viss ordning är det bra om du beskriver detta.
                        """
                )
                .build()
        )
        .children(List.of(children))
        .build();
  }
}

