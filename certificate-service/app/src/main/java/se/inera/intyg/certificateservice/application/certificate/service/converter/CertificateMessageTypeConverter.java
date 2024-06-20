package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMessageTypeDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;

@Component
public class CertificateMessageTypeConverter {

  public CertificateMessageTypeDTO convert(CertificateMessageType certificateMessageType) {
    return CertificateMessageTypeDTO.builder()
        .type(QuestionTypeDTO.valueOf(certificateMessageType.type().name()))
        .subject(certificateMessageType.subject().subject())
        .build();
  }
}
