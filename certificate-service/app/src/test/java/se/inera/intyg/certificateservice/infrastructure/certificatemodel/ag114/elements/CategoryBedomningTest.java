package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryBedomning.categoryBedomning;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryBedomningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shouldIncludeId() {
    final var element = categoryBedomning();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Bedömning")
        .description("""
            Utgångspunkten är att patientens arbetsförmåga ska bedömas i förhållande till patientens normala arbetstid.
            
            Under sjuklöneperioden kan läkaren bedöma arbetsförmågan steglöst. Det betyder att läkaren inte behöver begränsa sig till de fyra nivåer som gäller för Försäkringskassans bedömning av rätten till sjukpenning. En steglös bedömning kan innebära att patientens faktiska arbetsförmåga kan tas tillvara på ett mer optimalt sätt.
            """)
        .build();

    final var element = categoryBedomning();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}
