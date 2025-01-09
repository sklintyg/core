package se.inera.intyg.certificateservice.certificate.converter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueListDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueTableDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class PrintCertificateQuestionConverter {

  public Optional<PrintCertificateQuestionDTO> convert(
      ElementSpecification elementSpecification, Certificate certificate) {
    final var elementData = certificate.getElementDataById(elementSpecification.id());

    if (elementData.isEmpty()) {
      return Optional.empty();
    }

    final var value = elementData.get().value();
    final var simplifiedValue = elementSpecification.configuration().simplified(value);

    return simplifiedValue.map(elementSimplifiedValue -> PrintCertificateQuestionDTO.builder()
        .id(elementSpecification.id().id())
        .name(elementSpecification.configuration().name())
        .value(convertValue(elementSimplifiedValue))
        .subquestions(
            elementSpecification.children().stream()
                .map(child -> convert(child, certificate))
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

    throw new IllegalStateException("No converter for this simplified value type");
  }

}
