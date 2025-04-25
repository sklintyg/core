package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.FORTSATTNINGSBLAD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPagaendeBehandlingar {

  public static final ElementId QUESTION_PAGAENDE_BEHANDLING_ID = new ElementId("19");
  private static final FieldId QUESTION_PAGAENDE_BEHANDLING_FIELD_ID = new FieldId(
      "19.1");
  private static final short LIMIT = 4000;
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txt_PagaendeBehandling[0]");

  private QuestionPagaendeBehandlingar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPagaendeBehandlingar() {
    return ElementSpecification.builder()
        .id(QUESTION_PAGAENDE_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Pågående medicinska behandlingar/åtgärder")
                .description("Ange vad syftet är och om möjligt tidplan samt ansvarig vårdenhet.")
                .id(QUESTION_PAGAENDE_BEHANDLING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_PAGAENDE_BEHANDLING_ID,
                    LIMIT)
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
                .maxLength(4000)
                .overflowSheetFieldId(FORTSATTNINGSBLAD_ID)
                .build()
        )
        .build();
  }
}
