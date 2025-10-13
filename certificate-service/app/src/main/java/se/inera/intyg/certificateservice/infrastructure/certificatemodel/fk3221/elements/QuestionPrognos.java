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

public class QuestionPrognos {

  public static final ElementId QUESTION_PROGNOS_ID = new ElementId(
      "39");
  public static final FieldId QUESTION_PROGNOS_FIELD_ID = new FieldId("39.2");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtPrognos[0]");

  private QuestionPrognos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPrognos() {
    return ElementSpecification.builder()
        .id(QUESTION_PROGNOS_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PROGNOS_FIELD_ID)
                .name(
                    "Hur förväntas barnets funktionsnedsättning och aktivitetsbegränsningar utvecklas över tid?")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PROGNOS_ID,
                    QUESTION_PROGNOS_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PROGNOS_ID,
                    (short) 4000)
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