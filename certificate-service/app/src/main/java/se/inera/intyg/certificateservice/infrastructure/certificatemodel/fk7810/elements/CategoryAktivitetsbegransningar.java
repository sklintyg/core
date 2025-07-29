package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAktivitetsbegransningar {

  private static final ElementId AKTIVITETSBEGRANSNINGAR_CATEGORY_ID = new ElementId("KAT_5");

  private CategoryAktivitetsbegransningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAktivitetsbegransningar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(AKTIVITETSBEGRANSNINGAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Aktivitetsbegränsningar")
                .description(
                    """
                        Beskriv de aktivitetsbegränsningar som du bedömer att patienten har på grund av sina funktionsnedsättningar.
                        <ul>
                        <li>Ange om vissa aktiviteter medför risker för individen eller andra.</li><li>Beskriv om din bedömning är baserad på observationer, anamnes eller utredning gjord av någon annan, som till exempel psykolog, arbetsterapeut, audionom, syn- eller hörselpedagog.</li><li>Om det är möjligt, ange också svårighetsgraden på aktivitetsbegränsningarna (lätt, måttlig. stor eller total) samt om begräsningarna varierar.</li><li>Om det är möjligt ange även hur de kan korrigeras med hjälpmedel.</li></ul>
                        """
                )
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}