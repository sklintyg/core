package se.inera.intyg.certificateservice.certificate.converter;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueLabeledListDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueLabeledTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueListDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueTableDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class PrintCertificateQuestionConverter {

  public Optional<PrintCertificateQuestionDTO> convert(
      ElementSpecification elementSpecification, Certificate certificate,
      PdfGeneratorOptions pdfGeneratorOptions) {
    final var elementData = certificate.getElementDataById(elementSpecification.id());
    final var allElementData = certificate.elementData();

    if (elementShouldNotBeDisplayed(elementSpecification, allElementData)) {
      return Optional.empty();
    }

    return elementSpecification.simplifiedValue(elementData, allElementData, pdfGeneratorOptions)
        .map(elementSimplifiedValue -> PrintCertificateQuestionDTO.builder()
            .id(elementSpecification.id().id())
            .name(elementSpecification.configuration().name())
            .value(convertValue(elementSimplifiedValue))
            .subquestions(
                elementSpecification.children().stream()
                    .map(child -> convert(child, certificate, pdfGeneratorOptions))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList()
            )
            .build()
        );
  }

  private static boolean elementShouldNotBeDisplayed(ElementSpecification elementSpecification,
      List<ElementData> allElementData) {
    return elementSpecification.shouldValidate() != null && elementSpecification.shouldValidate()
        .negate()
        .test(allElementData);
  }

  private ElementSimplifiedValueDTO convertValue(ElementSimplifiedValue elementSimplifiedValue) {
    if (elementSimplifiedValue instanceof ElementSimplifiedValueText valueText) {
      return ElementSimplifiedValueTextDTO.builder()
          .text(valueText.text())
          .build();
    }

    if (elementSimplifiedValue instanceof ElementSimplifiedValueList valueList) {
      return ElementSimplifiedValueListDTO.builder()
          .list(
              valueList.list()
          )
          .build();
    }

    if (elementSimplifiedValue instanceof ElementSimplifiedValueTable valueTable) {
      return ElementSimplifiedValueTableDTO.builder()
          .headings(valueTable.headings())
          .values(valueTable.values())
          .build();
    }

    if (elementSimplifiedValue instanceof ElementSimplifiedValueLabeledList valueLabeledList) {
      return ElementSimplifiedValueLabeledListDTO.builder()
          .list(
              valueLabeledList.list().stream()
                  .map(labeledText -> ElementSimplifiedValueLabeledTextDTO.builder()
                      .label(labeledText.label())
                      .text(labeledText.text())
                      .build()
                  )
                  .toList()
          )
          .build();
    }

    if (elementSimplifiedValue instanceof ElementSimplifiedValueLabeledText valueText) {
      return ElementSimplifiedValueLabeledTextDTO.builder()
          .label(valueText.label())
          .text(valueText.text())
          .build();
    }

    throw new IllegalStateException("No converter for this simplified value type");
  }
}
