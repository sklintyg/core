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
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.DeleteMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.CreateMessageService;
import se.inera.intyg.certificateservice.application.message.service.DeleteAnswerService;
import se.inera.intyg.certificateservice.application.message.service.DeleteMessageService;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateFromMessageService;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageService;
import se.inera.intyg.certificateservice.application.message.service.HandleMessageService;
import se.inera.intyg.certificateservice.application.message.service.IncomingMessageService;
import se.inera.intyg.certificateservice.application.message.service.MessageExistsService;
import se.inera.intyg.certificateservice.application.message.service.SaveAnswerService;
import se.inera.intyg.certificateservice.application.message.service.SaveMessageService;
import se.inera.intyg.certificateservice.application.message.service.SendAnswerService;
import se.inera.intyg.certificateservice.application.message.service.SendMessageService;

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
  private final SaveMessageService saveMessageService;
  private final DeleteMessageService deleteMessageService;
  private final SendMessageService sendMessageService;
  private final SaveAnswerService saveAnswerService;
  private final DeleteAnswerService deleteAnswerService;
  private final SendAnswerService sendAnswerService;

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

  @PostMapping("/{messageId}/save")
  SaveMessageResponse saveMessage(
      @RequestBody SaveMessageRequest request,
      @PathVariable("messageId") String messageId) {
    return saveMessageService.save(request, messageId);
  }

  @PostMapping("/{messageId}/delete")
  void deleteMessage(
      @RequestBody DeleteMessageRequest request,
      @PathVariable("messageId") String messageId) {
    deleteMessageService.delete(request, messageId);
  }

  @PostMapping("/{messageId}/send")
  SendMessageResponse sendMessage(
      @RequestBody SendMessageRequest request,
      @PathVariable("messageId") String messageId) {
    return sendMessageService.send(request, messageId);
  }

  @PostMapping("/{messageId}/saveanswer")
  SaveAnswerResponse saveAnswer(
      @RequestBody SaveAnswerRequest request,
      @PathVariable("messageId") String messageId) {
    return saveAnswerService.save(request, messageId);
  }

  @PostMapping("/{messageId}/deleteanswer")
  DeleteAnswerResponse deleteAnswer(
      @RequestBody DeleteAnswerRequest request,
      @PathVariable("messageId") String messageId) {
    return deleteAnswerService.delete(request, messageId);
  }

  @PostMapping("/{messageId}/sendanswer")
  SendAnswerResponse sendAnswer(
      @RequestBody SendAnswerRequest request,
      @PathVariable("messageId") String messageId) {
    return sendAnswerService.send(request, messageId);
  }
}
