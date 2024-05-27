package se.inera.intyg.certificateservice.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageService;
import se.inera.intyg.certificateservice.application.message.service.IncomingMessageService;
import se.inera.intyg.certificateservice.application.message.service.MessageExistsService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageController {

  private final IncomingMessageService incomingMessageService;
  private final GetCertificateMessageService getCertificateMessageService;
  private final MessageExistsService messageExistsService;

  @PostMapping
  void receiveMessage(@RequestBody IncomingMessageRequest incomingMessageRequest) {
    incomingMessageService.receive(incomingMessageRequest);
  }

  @PostMapping("/{certificateId}")
  GetCertificateMessageResponse getMessagesForCertificate(
      @RequestBody GetCertificateMessageRequest getCertificateMessageRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateMessageService.get(getCertificateMessageRequest, certificateId);
  }

  @GetMapping("/{messageId}/exists")
  MessageExistsResponse findExistingMessage(
      @PathVariable("messageId") String certificateId) {
    return messageExistsService.exist(certificateId);
  }
}
