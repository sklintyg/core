package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionAnnanKroppsligFunktionMotivering extends
    AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID = new ElementId(
      "14");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_FIELD_ID = new FieldId(
      "14.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtIntellektuellFunktion[8]");

  private QuestionAnnanKroppsligFunktionMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAnnanKroppsligFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID,
        "Annan kroppslig funktion",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Med annan kroppslig funktion menas till exempel andningsfunktion, matsmältnings- och ämnesomsättningsfunktion samt blås- och tarmfunktion.",
        PDF_FIELD_ID
    );
  }
}
