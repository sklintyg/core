package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVardasBarnetInneliggandePaSjukhus {

  public static final ElementId QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID = new ElementId(
      "62");
  public static final FieldId QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_FIELD_ID = new FieldId(
      "62.1");

  private static final PdfFieldId PDF_VARDAS_BARNET_OPTION_TRUE = new PdfFieldId(
      "form1[0].#subform[3].ksr_Ja_1[0]");
  private static final PdfFieldId PDF_VARDAS_BARNET_OPTION_FALSE = new PdfFieldId(
      "form1[0].#subform[3].ksr_Nej_1[0]");

  private QuestionVardasBarnetInneliggandePaSjukhus() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionVardasBarnetInneliggandePaSjukhus(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Vårdas barnet inneliggande på sjukhus?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID,
                    QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_FIELD_ID
                )
            )
        )
        .pdfConfiguration(
            PdfConfigurationBoolean.builder()
                .checkboxTrue(PDF_VARDAS_BARNET_OPTION_TRUE)
                .checkboxFalse(PDF_VARDAS_BARNET_OPTION_FALSE)
                .build()
        )
        .includeWhenRenewing(false)
        .children(List.of(children))
        .build();
  }
}
