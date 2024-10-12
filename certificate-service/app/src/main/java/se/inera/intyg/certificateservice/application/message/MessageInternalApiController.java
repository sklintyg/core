package se.inera.intyg.certificateservice.application.message;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageInternalService;
import se.inera.intyg.certificateservice.application.message.service.GetMessageInternalXmlService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/message")
public class MessageInternalApiController {

  private final GetMessageInternalXmlService getMessageInternalXmlService;
  private final GetCertificateMessageInternalService getCertificateMessageInternalService;

  @PostMapping("/{messageId}/xml")
  @PerformanceLogging(eventAction = "internal-retrieve-message-xml", eventType = EVENT_TYPE_ACCESSED)
  GetMessageInternalXmlResponse getMessageXml(@PathVariable("messageId") String messageId) {
    return getMessageInternalXmlService.get(messageId);
  }

  @PostMapping("/{certificateId}")
  @PerformanceLogging(eventAction = "internal-retrieve-message-list-for-certificate", eventType = EVENT_TYPE_ACCESSED)
  GetCertificateMessageInternalResponse getMessagesForCertificate(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateMessageInternalService.get(certificateId);
  }
}
