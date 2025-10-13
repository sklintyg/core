package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryHotTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_2");

  @Test
  void shallIncludeId() {
    final var element = CategoryHot.categoryHot();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Påtagligt hot mot patientens liv")
        .description("""
            Ange på vilket sätt hälsotillståndet utgör ett påtagligt hot mot patientens liv i nuläget eller på viss tids sikt.
            
            Hälsotillståndet kan utgöra ett påtagligt hot även om det finns hopp om att det förbättras.
            <ul>
            <li>Ange alla diagnoser som sammantaget medför ett påtagligt hot mot patientens liv.</li><li>Ange ett av alternativen som gäller patientens behandling och vårdsituation.</li></ul>""")
        .build();

    final var element = CategoryHot.categoryHot();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}