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

public class QuestionHalsotillstandSomatiska {

  public static final ElementId QUESTION_HALSOTILLSTAND_SOMATISKA_ID = new ElementId("71");
  public static final FieldId QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID = new FieldId("71.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtBeskrivProvsvar[0]");

  private QuestionHalsotillstandSomatiska() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHalsotillstandSomatiska() {
    return ElementSpecification.builder()
        .id(QUESTION_HALSOTILLSTAND_SOMATISKA_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Barnets aktuella somatiska hälsotillstånd")
                .description(
                    "Beskriv barnets nuvarande somatiska hälsotillstånd. Ta med aktuella undersökningsfynd, testresultat och observationer som har betydelse för din bedömning av allvarligt sjukt barn. Om läkarutlåtandet avser misstanke, beskriv på vilket sätt undersökningsfynden innebär en konkret misstanke om ett specifikt sjukdomstillstånd.")
                .id(QUESTION_HALSOTILLSTAND_SOMATISKA_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_HALSOTILLSTAND_SOMATISKA_ID,
                    TEXT_FIELD_LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
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