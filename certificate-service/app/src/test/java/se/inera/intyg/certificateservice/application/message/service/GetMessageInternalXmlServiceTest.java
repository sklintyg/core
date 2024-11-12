package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7472_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.ANSWER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.XmlGeneratorMessage;

@ExtendWith(MockitoExtension.class)
class GetMessageInternalXmlServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private XmlGeneratorMessage xmlGeneratorMessage;
  @InjectMocks
  private GetMessageInternalXmlService getMessageInternalXmlService;

  private static final String XML = "<message>test</message>";

  @Test
  void shallReturnXmlWithAnswer() {
    final var expected = GetMessageInternalXmlResponse.builder()
        .xml(new Xml(XML).base64())
        .build();

    final var complementMessageWithAnswer = complementMessageBuilder()
        .answer(ANSWER)
        .build();

    doReturn(complementMessageWithAnswer).when(messageRepository)
        .findById(new MessageId(MESSAGE_ID));
    doReturn(FK7472_CERTIFICATE).when(certificateRepository).getById(FK7472_CERTIFICATE.id());
    doReturn(new Xml(XML)).when(xmlGeneratorMessage)
        .generateAnswer(ANSWER, complementMessageWithAnswer, FK7472_CERTIFICATE);

    assertEquals(expected,
        getMessageInternalXmlService.get(MESSAGE_ID)
    );
  }

  @Test
  void shallReturnXmlWithoutAnswer() {
    final var expected = GetMessageInternalXmlResponse.builder()
        .xml(new Xml(XML).base64())
        .build();

    final var complementMessageWithAnswer = complementMessageBuilder()
        .build();

    doReturn(complementMessageWithAnswer).when(messageRepository)
        .findById(new MessageId(MESSAGE_ID));
    doReturn(FK7472_CERTIFICATE).when(certificateRepository).getById(FK7472_CERTIFICATE.id());
    doReturn(new Xml(XML)).when(xmlGeneratorMessage)
        .generate(complementMessageWithAnswer, FK7472_CERTIFICATE);

    assertEquals(expected,
        getMessageInternalXmlService.get(MESSAGE_ID)
    );
  }
}