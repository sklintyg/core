package se.inera.intyg.certificateservice.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.application.message.service.CreateMessageService;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateFromMessageService;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageService;
import se.inera.intyg.certificateservice.application.message.service.HandleMessageService;
import se.inera.intyg.certificateservice.application.message.service.IncomingMessageService;
import se.inera.intyg.certificateservice.application.message.service.MessageExistsService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageController {

  private final IncomingMessageService incomingMessageService;
  private final GetCertificateMessageService getCertificateMessageService;
  private final MessageExistsService messageExistsService;
  private final GetCertificateFromMessageService getCertificateFromMessageService;
  private final HandleMessageService handleMessageService;
  private final CreateMessageService createMessageService;

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
      @PathVariable("messageId") String messageId) {
    return messageExistsService.exist(messageId);
  }

  @PostMapping("/{messageId}/certificate")
  GetCertificateFromMessageResponse getCertificateFromMessage(
      @RequestBody GetCertificateFromMessageRequest getCertificateFromMessageRequest,
      @PathVariable("messageId") String messageId) {
    return getCertificateFromMessageService.get(getCertificateFromMessageRequest, messageId);
  }

  @PostMapping("/{messageId}/handle")
  HandleMessageResponse handleMessage(
      @RequestBody HandleMessageRequest request,
      @PathVariable("messageId") String messageId) {
    return handleMessageService.handle(request, messageId);
  }

  @PostMapping("/{certificateId}/create")
  CreateMessageResponse createMessage(
      @RequestBody CreateMessageRequest request,
      @PathVariable("certificateId") String certificateId) {
    return createMessageService.create(request, certificateId);
  }
}
