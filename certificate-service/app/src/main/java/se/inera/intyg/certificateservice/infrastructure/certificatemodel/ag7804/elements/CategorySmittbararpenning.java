package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySmittbararpenning {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_1");

  private CategorySmittbararpenning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySmittbararpenning(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Smittbärarpenning")
                .description(
                    """
                        Fylls i om patienten kan arbeta men inte får göra det på grund av något av följande:
                        <ul><li>Att patienten har en allmänfarlig sjukdom och en läkare har beslutat om förhållningsregler enligt smittskyddslagen.</li><li>Att patienten genomgår en läkarundersökning eller hälsokontroll i syfte att klarlägga om hen är smittad av en allmänfarlig sjukdom.</li><li>Att patienten har en sjukdom, en smitta, eller ett sår som gör att hen inte får hantera livsmedel.</li></ul>
                        Ange vilken allmänfarlig sjukdom som patienten har eller kan antas ha i fältet för diagnos. Ange sedan för vilken period som förhållningsreglerna gäller i fältet för nedsättning av arbetsförmåga.
                        """
                )
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
