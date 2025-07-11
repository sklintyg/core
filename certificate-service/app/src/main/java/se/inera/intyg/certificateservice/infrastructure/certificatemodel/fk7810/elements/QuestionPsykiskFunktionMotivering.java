package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionPsykiskFunktionMotivering extends AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID = new ElementId(
      "11");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_FIELD_ID = new FieldId(
      "11.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida3[0].flt_txtAnnanPsykiskFunktion[0]");

  private QuestionPsykiskFunktionMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID,
        "Annan psykisk funktion",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        """
            Med annan psykisk funktion menas exempelvis:
            <ul>
            <li>stämningsläge, depressivitet, ångest och reglering av affekter</li><li>motivation, energinivå, impulskontroll och initiativförmåga</li><li>kognitiv flexibilitet, omdöme och insikt</li><li>minnesfunktioner</li><li>sömnfunktioner</li><li>vanföreställningar och tvångstankar</li><li>språklig funktion</li><li>orientering i tid samt till plats, situation och person.</li></ul>
            """,
        PDF_FIELD_ID
    );
  }
}