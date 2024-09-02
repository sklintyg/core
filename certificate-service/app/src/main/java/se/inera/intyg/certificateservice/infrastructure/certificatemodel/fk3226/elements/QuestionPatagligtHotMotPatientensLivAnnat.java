package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPatagligtHotMotPatientensLivAnnat {

  private static final ElementId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID = new ElementId(
      "52.7");
  private static final FieldId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_FIELD_ID = new FieldId(
      "52.7");
  private static final PdfFieldId PDF_CONDITION_IS_LIFE_THREATENING_OTHER_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtBeskrivSjukdomstillstandet[0]");

  private QuestionPatagligtHotMotPatientensLivAnnat() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPatagligtHotMotPatientensLivAnnat() {
    return ElementSpecification.builder()
        .id(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_FIELD_ID)
                .name(
                    "Beskriv på vilket sätt sjukdomstillståndet utgör ett påtagligt hot mot patientens liv")
                .label(
                    "Ange när tillståndet blev livshotande, och om det är möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller."
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID,
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    ANNAT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID,
                    (short) 265)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(265)
                    .build()
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(ANNAT_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_CONDITION_IS_LIFE_THREATENING_OTHER_FIELD_ID)
                .maxLength(265)
                .build()
        )
        .build();
  }
}
