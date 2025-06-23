package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.FK3221PdfSpecification.PDF_TEXT_FIELD_LENGTH;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionAktivitetsbegransningar {

  public static final ElementId QUESTION_AKTIVITETSBEGRANSNINGAR_ID = new ElementId("17");
  private static final FieldId QUESTION_AKTIVITETSBEGRANSNINGAR_FIELD_ID = new FieldId("17.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtKonkretEx[0]");

  private QuestionAktivitetsbegransningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAktivitetsbegransningar() {
    return ElementSpecification.builder()
        .id(QUESTION_AKTIVITETSBEGRANSNINGAR_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_AKTIVITETSBEGRANSNINGAR_FIELD_ID)
                .name(
                    "Beskriv vad barnet har svårt att göra på grund av sin funktionsnedsättning")
                .label(
                    "Ge konkreta exempel på aktiviteter i barnets vardag där svårigheter uppstår.")
                .build())
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_AKTIVITETSBEGRANSNINGAR_ID,
                    (short) 4000),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_AKTIVITETSBEGRANSNINGAR_ID,
                    QUESTION_AKTIVITETSBEGRANSNINGAR_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
                .overflowSheetFieldId(
                    new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
                .build()
        )
        .build();
  }
}