package se.inera.intyg.certificateservice.application.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
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
    final var message = messageRepository.findById(new MessageId(messageId));
    final var certificate = certificateRepository.getById(message.certificateId());

    return GetMessageInternalXmlResponse.builder()
        .xml(getXml(message, certificate))
        .build();
  }

  private String getXml(Message message, Certificate certificate) {
    return message.answer() != null
        ? xmlGeneratorMessage.generateAnswer(message.answer(), message, certificate)
        .base64()
        : xmlGeneratorMessage.generate(message, certificate).base64();
  }
}