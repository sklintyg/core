package se.inera.intyg.certificateservice.certificate.converter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class PrintCertificateQuestionConverter {

  public Optional<PrintCertificateQuestionDTO> convert(
      ElementSpecification elementSpecification, Optional<ElementData> elementData) {
    if (elementData.isEmpty()) {
      return Optional.empty();
    }

    final var value = elementData.get().value();
    final var simplifiedValue = elementSpecification.configuration().simplified(value);

    return simplifiedValue.map(elementSimplifiedValue -> PrintCertificateQuestionDTO.builder()
        .name(elementSpecification.configuration().name())
        .value(elementSimplifiedValue)
        .build()
    );

  }

}
