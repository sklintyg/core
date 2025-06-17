package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryFunktionsnedsattningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_4");

  @Test
  void shallIncludeId() {
    final var element = categoryFunktionsnedsattning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Funktionsnedsättning")
        .description(
            """
                Beskriv de funktionsnedsättningar som barnet har. Ange de observationer, undersökningsfynd eller testresultat som din bedömning är baserad på. Det kan till exempel vara:
                <ul>
                <li>avvikelser i somatisk och psykisk status inklusive gradering</li><li>röntgen- och laboratoriefynd</li><li>resultat av kliniskt fysiologiska undersökningar</li><li>andra testresultat, exempelvis neuropsykologiska.</li></ul>
                Ange även vilka uppgifter som är baserade på anamnes.
                
                Ange om möjligt grad av funktionsnedsättning (till exempel lätt, måttlig, stor eller total). Funktionsområdena följer väsentligen ICF, men vissa förenklingar har gjorts."""
        )
        .build();

    final var element = categoryFunktionsnedsattning();

    assertEquals(expectedConfiguration, element.configuration());
  }
}