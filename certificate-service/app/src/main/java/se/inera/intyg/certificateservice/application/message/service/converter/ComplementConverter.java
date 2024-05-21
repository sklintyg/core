package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.ComplementDTO;
import se.inera.intyg.certificateservice.domain.message.model.Complement;

@Component
public class ComplementConverter {

  public ComplementDTO convert(Complement complement) {
    return ComplementDTO.builder()
        .build();
  }
}
