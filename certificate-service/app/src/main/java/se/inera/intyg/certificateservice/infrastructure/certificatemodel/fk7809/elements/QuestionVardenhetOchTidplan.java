package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPagaendeOchPlaneradeBehandlingar.QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPagaendeOchPlaneradeBehandlingar.QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVardenhetOchTidplan {

  public static final ElementId QUESTION_VARDENHET_OCH_TIDPLAN_ID = new ElementId(
      "50.2");
  private static final FieldId QUESTION_VARDENHET_OCH_TIDPLAN_FIELD_ID = new FieldId("50.2");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida3[0].flt_txtVardenhetTidplan[0]");

  private QuestionVardenhetOchTidplan() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionVardenhetOchTidplan() {
    return ElementSpecification.builder()
        .id(QUESTION_VARDENHET_OCH_TIDPLAN_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_VARDENHET_OCH_TIDPLAN_FIELD_ID)
                .name("Ange ansvarig vårdenhet och tidplan")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_VARDENHET_OCH_TIDPLAN_ID,
                    QUESTION_VARDENHET_OCH_TIDPLAN_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_VARDENHET_OCH_TIDPLAN_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID,
                    QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID
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
        .mapping(
            new ElementMapping(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID, null)
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(53)
                .overflowSheetFieldId(
                    new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
                .build()
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID))
                .map(element -> (ElementValueText) element.value())
                .anyMatch(value -> value.text() != null && !value.text().isEmpty())
        ).build();
  }
}