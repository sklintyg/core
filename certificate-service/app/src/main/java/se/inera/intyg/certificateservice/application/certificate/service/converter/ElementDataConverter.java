package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Component
@RequiredArgsConstructor
public class ElementDataConverter {

  private final List<ElementValueConverter> elementValueConverter;

  public ElementData convert(String questionId, CertificateDataElement certificateDataElement) {
    final var value = certificateDataElement.getValue();
    return ElementData.builder()
        .id(new ElementId(questionId))
        .value(elementValueConverter.stream()
            .filter(valueConverter -> value.getType().equals(valueConverter.getType()))
            .findFirst()
            .map(converter -> converter.convert(value))
            .orElseThrow(() -> new IllegalStateException(
                "Could not find converter for type '%s'".formatted(value.getType()))
            )
        )
        .build();
  }
}
