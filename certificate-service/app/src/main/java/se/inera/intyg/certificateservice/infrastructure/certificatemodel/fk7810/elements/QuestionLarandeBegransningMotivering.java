package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_LARANDE_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionLarandeBegransningMotivering extends
    AbstractAktivitetsbegransningMotivering {

  public static final ElementId AKTIVITETSBEGRANSNING_MOTIVERING_LARANDE_BEGRANSNING_ID = new ElementId(
      "65");
  private static final FieldId AKTIVITETSBEGRANSNING_MOTIVERING_LARANDE_BEGRANSNING_FIELD_ID = new FieldId(
      "65.1");

  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida4[0].flt_txtLarandeKunskap[0]");

  private QuestionLarandeBegransningMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionLarandeBegransningMotivering() {
    return getFunktionsnedsattningMotivering(
        AKTIVITETSBEGRANSNING_MOTIVERING_LARANDE_BEGRANSNING_ID,
        AKTIVITETSBEGRANSNING_MOTIVERING_LARANDE_BEGRANSNING_FIELD_ID,
        AKTIVITETSBAGRENSNINGAR_LARANDE_ID,
        "Lärande, tillämpa kunskap samt allmänna uppgifter och krav",
        GENERAL_LABEL_AKTIVITETSBEGRANSNING,
        "Med lärande och tillämpa kunskap menas att till exempel att förvärva färdigheter, att lösa problem och att fatta beslut. Med allmänna uppgifter och krav menas till exempel att genomföra daglig rutin, eller att hantera stress och andra psykologiska krav.",
        PDF_FIELD_ID
    );
  }
}