package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryIdentitet {

  private static final ElementId IDENTITET_CATEGORY_ID = new ElementId(
      "KAT_0.2");

  private CategoryIdentitet() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryIdentitet(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(IDENTITET_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Identitet")
                .description("""
                    <b iu-className="iu-fw-heading">ID-kort</b>
                    Avser SIS-märkt ID-kort, svenskt nationellt ID-kort eller ID-kort utfärdat av Skatteverket.
                                        
                    <b iu-className="iu-fw-heading">Företagskort eller tjänstekort</b>
                    Avser SIS-märkt företagskort eller tjänstekort.
                                        
                    <b iu-className="iu-fw-heading">Försäkran enligt 18 kap 4 §</b>
                    Försäkran enligt 18 kap 4 § i Transportstyrelsens föreskrifter och allmänna råd om medicinska krav för innehav av körkort m.m. (TSFS2010:125). Identiteten får fastställas genom att en förälder, annan vårdnadshavare, make, maka eller sambo, registrerad partner eller myndigt barn skriftligen försäkrar att lämnade uppgifter om sökandens identitet är riktiga. Den som lämnar en sådan försäkran ska vara närvarande vid identitetskontrollen och kunna styrka sin egen identitet.
                                        
                    <b iu-className="iu-fw-heading">Pass</b>
                    EU-pass och pass utfärdat av Färöarna, Förenade kungariket, Island, Liechtenstein, Norge eller Schweiz
                    """)
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
