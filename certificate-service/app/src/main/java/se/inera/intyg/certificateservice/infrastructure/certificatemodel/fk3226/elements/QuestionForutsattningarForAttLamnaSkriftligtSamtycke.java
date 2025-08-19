package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionForutsattningarForAttLamnaSkriftligtSamtycke {

  public static final ElementId FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID = new ElementId(
      "53");
  public static final FieldId FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID = new FieldId(
      "53.1");
  private static final PdfFieldId PDF_CAN_CONSENT_YES_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Ja_Modul3[0]");
  private static final PdfFieldId PDF_CAN_CONSENT_NO_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Nej_Modul3[0]");

  private QuestionForutsattningarForAttLamnaSkriftligtSamtycke() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionForutsattningarForAttLamnaSkriftligtSamtycke() {
    return ElementSpecification.builder()
        .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID)
                .name(
                    "Har patienten de medicinska förutsättningarna för att kunna lämna samtycke?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID,
                    FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID
                )
            )
        )
        .includeWhenRenewing(false)
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationBoolean.builder()
                .checkboxTrue(PDF_CAN_CONSENT_YES_FIELD_ID)
                .checkboxFalse(PDF_CAN_CONSENT_NO_FIELD_ID)
                .build()
        )
        .build();
  }
}