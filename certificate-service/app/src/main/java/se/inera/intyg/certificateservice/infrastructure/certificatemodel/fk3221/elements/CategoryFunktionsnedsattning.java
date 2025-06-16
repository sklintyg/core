package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryFunktionsnedsattning {

  private static final ElementId FUNKTIONSNEDSATTNING_CATEGORY_ID = new ElementId("KAT_4");

  private CategoryFunktionsnedsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryFunktionsnedsattning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(FUNKTIONSNEDSATTNING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Funktionsnedsättning")
                .description(
                    """
                        Beskriv de funktionsnedsättningar som barnet har. Ange de observationer, undersökningsfynd eller testresultat som din bedömning är baserad på. Det kan till exempel vara:
                        <ul>
                        <li>avvikelser i somatisk och psykisk status inklusive gradering</li><li>röntgen- och laboratoriefynd</li><li>resultat av kliniskt fysiologiska undersökningar</li><li>andra testresultat, exempelvis neuropsykologiska.</li></ul>
                        Ange även vilka uppgifter som är baserade på anamnes.
                        
                        Ange om möjligt grad av funktionsnedsättning (till exempel lätt, måttlig, stor eller total). Funktionsområdena följer väsentligen ICF, men vissa förenklingar har gjorts."""
                )
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}