package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryIdentitetTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_0.2");

  @Test
  void shallIncludeId() {
    final var element = CategoryIdentitet.categoryIdentitet();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
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
        .build();

    final var element = CategoryIdentitet.categoryIdentitet();

    assertEquals(expectedConfiguration, element.configuration());
  }
}