package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionMedicinskBehandling {

  public static final ElementId QUESTION_MEDICINSK_BEHANDLING_ID = new ElementId("19");
  public static final FieldId QUESTION_MEDICINSK_BEHANDLING_FIELD_ID = new FieldId("19.1");

  private QuestionMedicinskBehandling() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicinskBehandling(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINSK_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Beskriv pågående och planerade medicinska behandlingar/åtgärder som kan påverka arbetsförmågan")
                .label("Ange vad syftet är och om möjligt tidsplan samt ansvarig vårdenhet.")
                .id(QUESTION_MEDICINSK_BEHANDLING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_MEDICINSK_BEHANDLING_ID,
                    (short) 4000),
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
                )
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
        .shouldValidate(
            ShouldValidateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false)
        )
        .children(List.of(children))
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(
                    new PdfFieldId("form1[0].Sida2[0].flt_txtPagandeMedicinskBehandling[0]"))
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
                .maxLength(8 * PDF_TEXT_FIELD_ROW_LENGTH)
                .build()
        )
        .build();
  }
}
