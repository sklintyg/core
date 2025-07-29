package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ANDNINGS_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionAndningsFunktionMotivering extends
    AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_ANDNINGS_FUNKTION_ID = new ElementId(
      "64");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_ANDNINGS_FUNKTION_FIELD_ID = new FieldId(
      "64.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida3[0].flt_txtAndningsfunktioner[0]");

  private QuestionAndningsFunktionMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAndningsFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_ANDNINGS_FUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_ANDNINGS_FUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_ANDNINGS_ID,
        "Andningsfunktion",
        "Beskriv funktionsnedsättningen, om möjligt med grad. Ange även eventuella undersökningsfynd och funktionstester.",
        """
            Med andningsfunktioner menas exempelvis:
            <ul>
            <li>funktioner att andas in luft i lungorna, gasutbyte mellan luft och blod samt utandning</li><li>funktioner i muskler som är involverade i andning</li></ul>
            Inklusive eventuella åtgärder av annan person, så som slemsugning och hjälp att hantera respirator
            """,
        PDF_FIELD_ID
    );
  }
}