package se.inera.intyg.certificateservice.application.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.XmlGeneratorMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMessageInternalXmlService {

  private final MessageRepository messageRepository;
  private final CertificateRepository certificateRepository;
  private final XmlGeneratorMessage xmlGeneratorMessage;

  public GetMessageInternalXmlResponse get(String messageId) {
    final var message = messageRepository.getById(new MessageId(messageId));
    final var certificate = certificateRepository.getById(message.certificateId());

    return GetMessageInternalXmlResponse.builder()
        .xml(
            xmlGeneratorMessage.generate(message.answer(), message, certificate).base64()
        )
        .build();
  }
}
