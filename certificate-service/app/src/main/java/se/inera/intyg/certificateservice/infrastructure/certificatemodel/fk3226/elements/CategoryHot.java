package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryHot {

  private static final ElementId CATEGORY_HOT_ID = new ElementId("KAT_2");

  private CategoryHot() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHot(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_HOT_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Påtagligt hot mot patientens liv")
                .description("""
                    Ange på vilket sätt hälsotillståndet utgör ett påtagligt hot mot patientens liv i nuläget eller på viss tids sikt.
                                        
                    Hälsotillståndet kan utgöra ett påtagligt hot även om det finns hopp om att det förbättras.
                    <ul>
                    <li>Ange alla diagnoser som sammantaget medför ett påtagligt hot mot patientens liv.</li><li>Ange ett av alternativen som gäller patientens behandling och vårdsituation.</li></ul>""")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
