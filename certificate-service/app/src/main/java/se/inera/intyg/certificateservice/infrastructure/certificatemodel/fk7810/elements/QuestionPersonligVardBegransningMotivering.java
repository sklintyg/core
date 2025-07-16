package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_PERSONAL_CARE_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionPersonligVardBegransningMotivering extends
    AbstractAktivitetsbegransningMotivering {

  public static final ElementId AKTIVITETSBEGRANSNING_MOTIVERING_PERSONLIG_VARD_BEGRANSNING_ID = new ElementId(
      "68");
  private static final FieldId AKTIVITETSBEGRANSNING_MOTIVERING_PERSONLIG_VARD_BEGRANSNING_FIELD_ID = new FieldId(
      "68.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida4[0].flt_txtPersonligVard[0]");

  private QuestionPersonligVardBegransningMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPersonligVardBegransningMotivering() {
    return getFunktionsnedsattningMotivering(
        AKTIVITETSBEGRANSNING_MOTIVERING_PERSONLIG_VARD_BEGRANSNING_ID,
        AKTIVITETSBEGRANSNING_MOTIVERING_PERSONLIG_VARD_BEGRANSNING_FIELD_ID,
        AKTIVITETSBAGRENSNINGAR_PERSONAL_CARE_ID,
        "Personlig vård och sköta sin hälsa",
        GENERAL_LABEL_AKTIVITETSBEGRANSNING,
        "Med personlig vård menas till exempel att tvätta och torka sig själv, att ta hand om sin kropp och sina kroppsdelar, att klä sig, att äta och dricka samt att sköta sin egen hälsa.",
        PDF_FIELD_ID
    );
  }
}