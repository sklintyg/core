package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

public class QuestionPagaendeOchPlaneradeBehandlingar {

  public static final ElementId QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_ID = new ElementId("19");
  private static final FieldId QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_FIELD_ID = new FieldId(
      "19.1");
  private static final short LIMIT = 4000;
  private static final PdfFieldId QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_PDF_FIELD_ID =
      new PdfFieldId("form1[0].#subform[2].flt_txtBeskrivBarnetsHalsotillstand[0]");

  private QuestionPagaendeOchPlaneradeBehandlingar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPagaendeOchPlaneradeBehandlingar() {
    return ElementSpecification.builder()
        .id(QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Ange pågående och planerade behandlingar")
                .id(QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_ID,
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
                .pdfFieldId(QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_PDF_FIELD_ID)
                .maxLength(4000)
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[3].flt_txtFortsattningsblad[0]"))
                .build()
        )
        .build();
  }
}
