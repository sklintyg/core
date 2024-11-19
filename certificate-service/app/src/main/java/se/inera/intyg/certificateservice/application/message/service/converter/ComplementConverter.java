package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.ComplementDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.message.model.Complement;

@Component
public class ComplementConverter {

  public ComplementDTO convert(Complement complement, Certificate certificate) {
    return ComplementDTO.builder()
        .questionId(complement.elementId().id())
        .questionText(
            certificate.certificateModel().elementSpecificationExists(complement.elementId()) ?
                certificate.certificateModel()
                    .elementSpecification(complement.elementId())
                    .configuration()
                    .name() :
                complement.elementId().id()
        )
        .message(complement.content().content())
        .build();
  }
}
