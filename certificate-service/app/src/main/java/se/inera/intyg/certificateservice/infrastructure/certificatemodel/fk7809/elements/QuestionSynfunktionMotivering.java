package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_SYNFUNKTION_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionSynfunktionMotivering extends AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_ID = new ElementId(
      "49");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_FIELD_ID = new FieldId(
      "49.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtIntellektuellFunktion[5]");

  private QuestionSynfunktionMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSynfunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_SYNFUNKTION_ID,
        "Synfunktion",
        "Beskriv funktionsnedsättningen och undersökningsfynd.",
        """
            Synfunktion handlar om förmågan att förnimma närvaro av ljus och synintryckets form, storlek, utformning och färg. För att Försäkringskassan ska kunna bedöma om det finns rätt till garanterad nivå av merkostnadsersättning är följande information viktig.
                                        
            Beskriv nedsättningen av synen:
            <ul>
            <li>synskärpa på långt håll med bästa korrektion</li><li>synfält – du kan skicka in en kopia av perimetri eller beskriva synfältet på annat sätt till exempel enligt Donders metod.</li><li>andra synfunktioner som till exempel förmåga till samsyn, dubbelseende, nystagmus, mörkerseende, färgseende, kontrastseende, bländningskänslighet och perception.</li></ul>                                          
            Beskriv:
            <ul>
            <li>personens förmåga att orientera sig och förflytta sig med hjälp av synen</li><li>hur stora svårigheter personen har att orientera sig och förflytta sig i en främmande miljö.</li></ul>
            Bedöm sambandet mellan svårigheterna och den nedsatta synfunktionen. Ange den ledsagning eller hjälpmedel som personen behöver när hen ska förflytta sig. Det kan till exempel vara markeringskäpp, teknikkäpp eller ledarhund.                       
            """,
        PDF_FIELD_ID
    );
  }
}
