package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;

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

public abstract class AbstractFunktionsnedsattningMotivering {

  protected static final String GENERAL_LABEL_FUNKTIONSNEDSATTNING = "Beskriv funktionsnedsättningen, om möjligt med grad. Ange även eventuella undersökningsfynd.";

  AbstractFunktionsnedsattningMotivering() {
    throw new IllegalStateException("Utility class");
  }

  protected static ElementSpecification getFunktionsnedsattningMotivering(ElementId questionId,
      FieldId questionFieldId, FieldId parentFieldId, String name,
      String label, String description, PdfFieldId pdfFieldId) {
    return ElementSpecification.builder()
        .id(questionId)
        .visibilityConfiguration(
            ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
                .elementId(FUNKTIONSNEDSATTNING_ID)
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
                    FUNKTIONSNEDSATTNING_ID,
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
                .filter(data -> data.id().equals(FUNKTIONSNEDSATTNING_ID))
                .map(element -> (ElementValueCodeList) element.value())
                .anyMatch(value -> value.list()
                    .stream()
                    .anyMatch(codeValue -> codeValue.codeId().equals(parentFieldId))
                )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(pdfFieldId)
                .maxLength(265)
                .overflowSheetFieldId(
                    new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
                .build()
        )
        .build();
  }
}