package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_OVRIG_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionOvrigaBegransningMotivering extends
    AbstractAktivitetsbegransningMotivering {

  public static final ElementId AKTIVITETSBEGRANSNING_MOTIVERING_OVRIGT_BEGRANSNING_ID = new ElementId(
      "69");
  private static final FieldId AKTIVITETSBEGRANSNING_MOTIVERING_OVRIGT_BEGRANSNING_FIELD_ID = new FieldId(
      "69.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida4[0].flt_txtOvrigaAktivitetsbegränsningar[0]");

  private QuestionOvrigaBegransningMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionOvrigaBegransningMotivering() {
    return getFunktionsnedsattningMotivering(
        AKTIVITETSBEGRANSNING_MOTIVERING_OVRIGT_BEGRANSNING_ID,
        AKTIVITETSBEGRANSNING_MOTIVERING_OVRIGT_BEGRANSNING_FIELD_ID,
        AKTIVITETSBAGRENSNINGAR_OVRIG_ID,
        "Övriga aktivitetsbegränsningar",
        GENERAL_LABEL_AKTIVITETSBEGRANSNING,
        """
            Övriga aktivitetsbegränsningar kan vara begränsningar i exempelvis att genomföra
            <ul>
            <li>husliga och dagliga sysslor och uppgifter</li><li>handlingar och uppgifter som behövs för grundläggande och sammansatta interaktioner med människor på ett i sammanhanget lämpligt och socialt passande sätt</li><li>handlingar och uppgifter som krävs för att engagera sig i organiserat socialt liv utanför familjen - i samhällsgemenskap, socialt och medborgerligt liv</li><li>uppgifter och handlingar som krävs vid utbildning, arbete, anställning och ekonomiska transaktioner</li></ul>
            """,
        PDF_FIELD_ID
    );
  }
}