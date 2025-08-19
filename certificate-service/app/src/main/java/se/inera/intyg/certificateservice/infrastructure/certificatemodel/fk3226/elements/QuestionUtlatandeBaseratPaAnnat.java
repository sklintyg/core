package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.QUESTION_UTLATANDE_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

public class QuestionUtlatandeBaseratPaAnnat {

  public static final ElementId QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID = new ElementId("1.3");
  public static final FieldId QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("1.3");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_OTHER_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtAnnatAngeVad[0]");

  private QuestionUtlatandeBaseratPaAnnat() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionUtlatandeBaseratPaAnnat() {
    return ElementSpecification.builder()
        .id(QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID)
                .name(
                    "Ange vad annat är")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID,
                    QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID,
                    (short) 50),
                CertificateElementRuleFactory.show(
                    QUESTION_UTLATANDE_BASERAT_PA_ID,
                    UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(50)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(
                QUESTION_UTLATANDE_BASERAT_PA_ID,
                CodeSystemKvFkmu0001.ANNAT
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_UTLATANDE_BASERAT_PA_ID))
                .map(element -> (ElementValueDateList) element.value())
                .anyMatch(value -> value.dateList().stream().anyMatch(
                    valueDate -> valueDate.dateId().equals(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID))
                )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_STATEMENT_BASED_ON_OTHER_FIELD_ID)
                .build()
        )
        .includeWhenRenewing(false)
        .build();
  }
}
