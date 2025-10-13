package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryFunktionsnedsattningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shallIncludeId() {
    final var element = CategoryFunktionsnedsattning.categoryFunktionsnedsattning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Funktionsnedsättning")
        .description(
            """
                Beskriv de funktionsnedsättningar som patienten har. Ange om din bedömning är baserad på observationer, undersökningsfynd eller testresultat. Det kan till exempel vara:
                <ul>
                <li>avvikelser i somatiskt och psykiskt status</li><li>röntgen- och laboratoriefynd</li><li>resultat av kliniskt fysiologiska undersökningar</li><li>andra testresultat, exempelvis neuropsykologiska.</li></ul>
                Ange även vilka uppgifter som är baserade på anamnes. Ange om möjligt grad av funktionsnedsättning (till exempel lätt, måttlig, stor eller total).
                
                Funktionsområdenas hjälptexter följer väsentligen ICF men då kategorierna i läkarutlåtandena är färre har vissa förenklingar gjorts.""")
        .build();

    final var element = CategoryFunktionsnedsattning.categoryFunktionsnedsattning();

    assertEquals(expectedConfiguration, element.configuration());
  }
}