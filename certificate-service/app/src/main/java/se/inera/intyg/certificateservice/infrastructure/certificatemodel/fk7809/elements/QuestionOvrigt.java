package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionOvrigt {

  public static final ElementId QUESTION_OVRIGT_ID = new ElementId(
      "25");
  private static final FieldId QUESTION_OVRIGT_FIELD_ID = new FieldId("25.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida4[0].flt_OvrigaUpplysningar[0]");

  private QuestionOvrigt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionOvrigt() {
    return ElementSpecification.builder()
        .id(QUESTION_OVRIGT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_OVRIGT_FIELD_ID)
                .name(
                    "Övriga upplysningar")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_OVRIGT_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(371)
                .overflowSheetFieldId(
                    new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
                .build()
        )
        .build();
  }
}