package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySjukvardandeInsats {

  private static final ElementId SJUKVARDANDE_INSATS_CATEGORY_ID = new ElementId("KAT_8");

  private CategorySjukvardandeInsats() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySjukvardandeInsats(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(SJUKVARDANDE_INSATS_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Sjukvårdande insatser inom personlig assistans")
                .description("""
                    Assistansersättning kan inte lämnas för sjukvårdande insatser enligt hälso- och sjukvårdslagen, HSL (51 kap. 5 § socialförsäkringsbalken). Om en hälso- och sjukvårdsåtgärd bedöms utföras som egenvård kan assistansersättning i vissa fall beviljas för detta hjälpbehov.
                    
                    Bestämmelserna om hälso- och sjukvårdsåtgärder som utförs i form av egenvård finns i lag (2022:1250) om egenvård. Med egenvård avses en hälso- och sjukvårdsåtgärd som behandlande hälso- och sjukvårdspersonal har bedömt att en patient kan utföra själv eller med hjälp av någon annan.
                    
                    En hälso- och sjukvårdsåtgärd är en åtgärd för att medicinskt förebygga, utreda eller behandla sjukdomar eller skador. Med hälso- och sjukvårdspersonal avses den som har legitimation för ett yrke inom hälso- och sjukvården eller som enligt särskilt förordnande har fått motsvarande behörighet. (2 – 4 §§ lag (2022:1250) om egenvård).""")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
