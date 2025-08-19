package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionVardEllerTillsyn.QUESTION_VARD_ELLER_TILLSYN_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfRadioOption;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVardasBarnetInneliggandePaSjukhus {

  public static final ElementId QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID = new ElementId(
      "62.1");
  public static final FieldId QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_FIELD_ID = new FieldId(
      "62.1");

  private static final PdfFieldId PDF_VARDAS_BARNET_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].RadioButtonList_2[0]");
  private static final PdfRadioOption PDF_VARDAS_BARNET_OPTION_TRUE = new PdfRadioOption("1");
  private static final PdfRadioOption PDF_VARDAS_BARNET_OPTION_FALSE = new PdfRadioOption("2");

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
        .mapping(new ElementMapping(QUESTION_VARD_ELLER_TILLSYN_ID, null))
        .pdfConfiguration(
            PdfConfigurationRadioBoolean.builder()
                .pdfFieldId(PDF_VARDAS_BARNET_FIELD_ID)
                .optionTrue(PDF_VARDAS_BARNET_OPTION_TRUE)
                .optionFalse(PDF_VARDAS_BARNET_OPTION_FALSE)
                .build()
        )
        .includeWhenRenewing(false)
        .children(List.of(children))
        .build();
  }
}
