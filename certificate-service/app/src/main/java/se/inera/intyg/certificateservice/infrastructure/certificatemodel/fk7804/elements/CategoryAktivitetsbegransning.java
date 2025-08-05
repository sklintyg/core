package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAktivitetsbegransning {

  public static final ElementId CATEGORY_ID = new ElementId("KAT_6");

  private CategoryAktivitetsbegransning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAktivitetsbegransning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Aktivitetsbegränsning")
                .description("""
                    Beskriv aktivitetsbegränsningens svårighetsgrad, till exempel lätt, medelsvår, svår, total eller motsvarande.
                    
                    Om patienten är arbetssökande eller har varit sjukskriven en längre tid kan det vara svårt att beskriva aktivitetsbegränsningar enbart i relation till patientens nuvarande arbetsuppgifter. Då kan det vara bra att även beskriva hur aktivitetsbegränsningarna yttrar sig i andra situationer, t. ex vardagliga situationer.\s
                    """)
                .build()
        )
        .children(List.of(children))
        .build();
  }
}

