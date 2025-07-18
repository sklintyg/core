package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionKommunikationBegransningMotivering extends
    AbstractAktivitetsbegransningMotivering {

  public static final ElementId AKTIVITETSBEGRANSNING_MOTIVERING_KOMMUNIKATION_BEGRANSNING_ID = new ElementId(
      "66");
  private static final FieldId AKTIVITETSBEGRANSNING_MOTIVERING_KOMMUNIKATION_BEGRANSNING_FIELD_ID = new FieldId(
      "66.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida4[0].flt_txtKommunikation[0]");

  private QuestionKommunikationBegransningMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKommunikationBegransningMotivering() {
    return getFunktionsnedsattningMotivering(
        AKTIVITETSBEGRANSNING_MOTIVERING_KOMMUNIKATION_BEGRANSNING_ID,
        AKTIVITETSBEGRANSNING_MOTIVERING_KOMMUNIKATION_BEGRANSNING_FIELD_ID,
        AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID,
        "Kommunikation",
        GENERAL_LABEL_AKTIVITETSBEGRANSNING,
        "Med kommunikation menas till exempel att kommunicera genom språk, tecken och symboler. Det innefattar att ta emot och att förmedla budskap, att genomföra samtal samt att använda olika metoder och hjälpmedel för detta.",
        PDF_FIELD_ID
    );
  }
}