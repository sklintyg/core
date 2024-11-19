package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionHorselFunktionMotivering extends AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_ID = new ElementId(
      "48");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_FIELD_ID = new FieldId(
      "48.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtIntellektuellFunktion[4]");

  private QuestionHorselFunktionMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID,
        "Hörselfunktion",
        "Beskriv funktionsnedsättningen och undersökningsfynd.",
        """
            Hörselfunktion handlar om förmågan att förnimma närvaro av ljud och att urskilja lokalisering, tonhöjd, ljudstyrka och ljudkvalitet.
                                        
            För att Försäkringskassan ska kunna bedöma om det finns rätt till garanterad nivå av merkostnadsersättning är följande information viktig. 
                  
            Beskriv eventuell nedsättning av hörseln utifrån hörselmätningar och öronstatus. Motivera konstaterade diagnoser utifrån hörseltesterna. Värdera sambandet mellan hörseltesterna och eventuella avvikelser. Beskriv förmågan till kommunikation och taluppfattning utifrån observation och resultat av mätningar eller tester. Skriv om objektiva hörselmätningar har gjorts. Ange vilken typ av hörhjälpmedel patienten använder, och om hen erbjudits utredning för cochleaimplantat (CI).
                    
            Du kan skicka in
            <ul>
            <li>resultat av hörseltester – tonaudiogram med ben och luftledning</li><li>resultat av maximal taluppfattning med angiven stimuleringsnivå i decibel (dB)</li><li>taluppfattning i ljudfält 65dB med optimalt anpassade hörhjälpmedel</li><li>resultat av eventuella objektiva hörselmätningar</li><li>underlag från andra bedömningar som gäller kommunikation.</li></ul>
            """,
        PDF_FIELD_ID
    );
  }
}
