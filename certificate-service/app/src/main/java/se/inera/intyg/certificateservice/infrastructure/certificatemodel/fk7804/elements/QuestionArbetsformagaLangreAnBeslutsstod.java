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

public class QuestionArbetsformagaLangreAnBeslutsstod {

  public static final ElementId QUESTION_ARBETFORMAGA_LANGRE_ID = new ElementId("37");
  private static final FieldId QUESTION_ARBETFORMAGA_LANGRE_FIELD_ID = new FieldId("37.1");

  private QuestionArbetsformagaLangreAnBeslutsstod() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionArbetsformagaLangreAnBeslutsstod() {
    return ElementSpecification.builder()
        .id(QUESTION_ARBETFORMAGA_LANGRE_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Patientens arbetsförmåga bedöms nedsatt längre tid än den som Socialstyrelsens försäkringsmedicinska beslutsstöd anger, därför att")
                .description("""
                    <ul><li>Om sjukdomen inte följer förväntat förlopp ska det framgå på vilket sätt.</li><li>Om det inträffar komplikationer som gör att det tar längre tid att återfå arbetsförmågan ska du beskriva detta.</li><li>Om sjukskrivningslängden påverkas av flera sjukdomar, så kallad samsjuklighet, ska du beskriva detta.</li></ul>
                    """)
                .id(QUESTION_ARBETFORMAGA_LANGRE_FIELD_ID)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId("form1[0].Sida3[0].flt_txtArbetsförmaga[0]"))
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
                .maxLength(3 * PDF_TEXT_FIELD_ROW_LENGTH)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_ARBETFORMAGA_LANGRE_ID,
                    (short) 4000
                ),
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
        .build();
  }
}
