package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAktivitetsbegransningarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shallIncludeId() {
    final var element = categoryAktivitetsbegransningar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Aktivitetsbegränsningar")
        .description(
            """
                Beskriv de aktivitetsbegränsningar som du bedömer att patienten har på grund av sina funktionsnedsättningar.
                <ul>
                <li>Ange om vissa aktiviteter medför risker för individen eller andra.</li><li>Beskriv om din bedömning är baserad på observationer, anamnes eller utredning gjord av någon annan, som till exempel psykolog, arbetsterapeut, audionom, syn- eller hörselpedagog.</li><li>Om det är möjligt, ange också svårighetsgraden på aktivitetsbegränsningarna (lätt, måttlig. stor eller total) samt om begräsningarna varierar.</li><li>Om det är möjligt ange även hur de kan korrigeras med hjälpmedel.</li></ul>
                """)
        .build();

    final var element = categoryAktivitetsbegransningar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}