package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.CertificateModelFactoryFK7427.TEXT_FIELD_LIMIT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.FK7427PdfSpecification.ROW_MAX_LENGTH;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVardEllerTillsyn {

  public static final ElementId QUESTION_VARD_ELLER_TILLSYN_ID = new ElementId("62");
  public static final FieldId QUESTION_VARD_ELLER_TILLSYN_FIELD_ID = new FieldId("62.5");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtVilkenVardTillsyn[0]");

  private QuestionVardEllerTillsyn() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionVardEllerTillsyn() {
    return ElementSpecification.builder()
        .id(QUESTION_VARD_ELLER_TILLSYN_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Beskriv barnets behov av vård eller tillsyn")
                .description(
                    "Beskriv vilken vård eller tillsyn som barnet behöver av förälder samt omfattning av denna vård eller tillsyn.")
                .id(QUESTION_VARD_ELLER_TILLSYN_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_VARD_ELLER_TILLSYN_ID,
                    QUESTION_VARD_ELLER_TILLSYN_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_VARD_ELLER_TILLSYN_ID,
                    TEXT_FIELD_LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit((int) TEXT_FIELD_LIMIT)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(ROW_MAX_LENGTH * 8)
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[3].flt_txtFortsattningsblad[0]"))
                .build()
        )
        .build();
  }
}

