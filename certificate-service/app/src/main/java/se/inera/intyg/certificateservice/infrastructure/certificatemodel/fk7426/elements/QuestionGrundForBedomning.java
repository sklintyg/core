package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.CertificateModelFactoryFK7426.TEXT_FIELD_LIMIT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.FORTSATTNINGSBLAD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.ROW_MAX_LENGTH;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionGrundForBedomning {

  public static final ElementId QUESTION_GRUND_FOR_BEDOMNING_ID = new ElementId("60");
  private static final FieldId QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID = new FieldId("60.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtBarnetsHalsotillstand[1]");

  private QuestionGrundForBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForBedomning() {
    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_BEDOMNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Varför bedömer du att barnet är allvarligt sjukt eller att det finns en stark misstanke om allvarlig diagnos?")
                .description(
                    "Beskriv varför du bedömer att barnet är allvarligt sjukt utifrån det påtagliga hotet mot barnets liv eller om det utan behandling finns fara för barnets liv. Om det påtagliga livshotet upphör men barnet fortsatt har behandling eller stor påverkan efter sjukdom så behöver du beskriva detta.")
                .id(QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_GRUND_FOR_BEDOMNING_ID,
                    QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_GRUND_FOR_BEDOMNING_ID,
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
                .overflowSheetFieldId(FORTSATTNINGSBLAD_ID)
                .build()
        )
        .build();
  }
}
