package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public abstract class AbstractAktivitetsbegransningMotivering {

  protected static final String GENERAL_LABEL_AKTIVITETSBEGRANSNING = "Beskriv aktivitetsbegränsningen. Ange grad om det är möjligt och hur aktivitetsbegränsningen kan korrigeras med hjälpmedel.";

  AbstractAktivitetsbegransningMotivering() {
    throw new IllegalStateException("Utility class");
  }

  protected static ElementSpecification getFunktionsnedsattningMotivering(ElementId questionId,
      FieldId questionFieldId, FieldId parentFieldId, String name,
      String label, String description, PdfFieldId pdfFieldId) {
    return ElementSpecification.builder()
        .id(questionId)
        .visibilityConfiguration(
            ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
                .elementId(AKTIVITETSBAGRENSNINGAR_ID)
                .fieldId(parentFieldId)
                .build()
        )
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(questionFieldId)
                .name(name)
                .label(label)
                .description(description)
                .build())
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    questionId,
                    (short) 4000),
                CertificateElementRuleFactory.mandatory(
                    questionId,
                    questionFieldId
                ),
                CertificateElementRuleFactory.show(
                    AKTIVITETSBAGRENSNINGAR_ID,
                    parentFieldId
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
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(AKTIVITETSBAGRENSNINGAR_ID))
                .map(element -> (ElementValueCodeList) element.value())
                .anyMatch(value -> value.list()
                    .stream()
                    .anyMatch(codeValue -> codeValue.codeId().equals(parentFieldId))
                )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(pdfFieldId)
                .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
                .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
                .build()
        )
        .build();
  }
}