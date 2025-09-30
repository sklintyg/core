package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionMedicinskaSkalForSvarareAtergang {

  public static final ElementId QUESTION_MEDICINSKA_SKAL_ID = new ElementId("33.2");
  private static final FieldId QUESTION_MEDICINSKA_SKAL_FIELD_ID = new FieldId("33.2");

  private QuestionMedicinskaSkalForSvarareAtergang() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicinskaSkalForSvarareAtergang() {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINSKA_SKAL_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Beskriv de medicinska skälen till att möjligheterna till återgång i arbete försämras")
                .id(QUESTION_MEDICINSKA_SKAL_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MEDICINSKA_SKAL_ID,
                    QUESTION_MEDICINSKA_SKAL_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MEDICINSKA_SKAL_ID,
                    (short) 4000
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID,
                    QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID
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
                .pdfFieldId(new PdfFieldId("form1[0].Sida3[0].flt_txtBeskrivMedicinskaSkal[0]"))
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
                .maxLength(3 * PDF_TEXT_FIELD_ROW_LENGTH)
                .build()
        )
        .shouldValidate(
            ShouldValidateFactory.valueBoolean(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID,
                true))
        .mapping(new ElementMapping(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID, null))
        .build();
  }
}
