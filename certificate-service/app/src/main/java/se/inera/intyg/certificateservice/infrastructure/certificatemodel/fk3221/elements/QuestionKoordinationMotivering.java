package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_KOORDINATION_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionKoordinationMotivering extends AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID = new ElementId(
      "13");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_FIELD_ID = new FieldId(
      "13.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtBalans[0]");

  private QuestionKoordinationMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKoordinationMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_FIELD_ID,
        FUNKTIONSNEDSATTNING_KOORDINATION_ID,
        "Balans, koordination och motorik",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Med balans menas kroppens balansfunktion och förnimmelse av kroppsställning (positionsuppfattning). Med koordination menas till exempel ögahandkoordination, gångkoordination och att samordna rörelser av armar och ben. Med motorik menas fin- och grovmotorik eller till exempel munmotorik.",
        PDF_FIELD_ID
    );
  }
}
