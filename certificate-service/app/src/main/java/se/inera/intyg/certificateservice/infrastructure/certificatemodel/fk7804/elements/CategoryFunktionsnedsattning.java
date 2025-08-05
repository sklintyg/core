package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryFunktionsnedsattning {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_5");

  private CategoryFunktionsnedsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryFunktionsnedsattning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Funktionsnedsättning")
                .description(
                    """
                        Funktionsnedsättning definieras enligt Internationell klassifikation av funktionstillstånd, funktionshinder och hälsa (ICF) som en betydande avvikelse eller förlust i kroppsfunktion och kan vara fysisk, psykisk eller kognitiv. Se även Socialstyrelsens försäkringsmedicinska kunskapsstöd.
                        
                        Försäkringskassan efterfrågar utöver din bedömning av vilka funktionsnedsättningar sjukdomen eller sjukdomarna leder till, även de medicinska uppgifter som du baserar bedömningen på.
                        
                        Om din bedömning baseras på annat än dina egna observationer och undersökningsfynd, exempelvis testresultat och anamnesuppgifter, beskriv hur du bedömer uppgifterna.
                        
                        Om uppgifterna är hämtade från någon annan inom hälso- och sjukvården, beskriv från vem och när de noterats.
                        """
                )
                .build()
        )
        .children(List.of(children))
        .build();
  }
}

