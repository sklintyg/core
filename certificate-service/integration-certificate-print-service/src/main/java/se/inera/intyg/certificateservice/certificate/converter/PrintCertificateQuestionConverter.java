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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueLabeledList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.HiddenElement;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class PrintCertificateQuestionConverter {

  public Optional<PrintCertificateQuestionDTO> convert(
      ElementSpecification elementSpecification, Certificate certificate,
      List<ElementId> hiddenElementIds) {
    final var elementData = certificate.getElementDataById(elementSpecification.id());

    if (elementData.isEmpty()) {
      return Optional.empty();
    }

    final var hiddenElementsForPrint = certificate.certificateModel().hiddenElementsForPrint();
    final var hiddenElement = hiddenElement(elementSpecification.id(), hiddenElementIds,
        hiddenElementsForPrint);
    final var value = elementData.get().value();
    final var simplifiedValue = hiddenElement.map(HiddenElement::value)
        .or(() -> elementSpecification.configuration().simplified(value));

    return simplifiedValue.map(elementSimplifiedValue -> PrintCertificateQuestionDTO.builder()
        .id(elementSpecification.id().id())
        .name(elementSpecification.configuration().name())
        .value(convertValue(elementSimplifiedValue))
        .subquestions(
            elementSpecification.children().stream()
                .map(child -> convert(child, certificate, hiddenElementIds))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()
        )
        .build()
    );

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

    throw new IllegalStateException("No converter for this simplified value type");
  }

  private Optional<HiddenElement> hiddenElement(ElementId elementId,
      List<ElementId> hiddenElements, List<HiddenElement> hiddenElementsForPrint) {
    return hiddenElementsForPrint.stream()
        .filter(hiddenElement -> hiddenElement.id().equals(elementId))
        .filter(hiddenElement -> hiddenElements.contains(hiddenElement.hiddenBy()))
        .findFirst();
  }
}
