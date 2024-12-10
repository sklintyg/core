package se.inera.intyg.certificateservice.certificate.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class PrintCertificateQuestionConverter {

  // TODO: Do we want to return optional? If no elemendata then the question is not answered => should not be printed
  public PrintCertificateQuestionDTO convert(
      ElementSpecification elementSpecification,
      Certificate certificate) { // TODO: We can accept elementData instead of whole certificate
    final var data = certificate.getElementDataById(elementSpecification.id());

    if (data.isEmpty()) {
      return null;
    }

    final var value = data.get().value();
    return PrintCertificateQuestionDTO.builder()
        .name(elementSpecification.configuration().name())
        .value(elementSpecification.configuration().simplified(value))
        .build();
  }

}
