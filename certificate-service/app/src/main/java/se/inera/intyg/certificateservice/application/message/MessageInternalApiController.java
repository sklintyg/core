package se.inera.intyg.certificateservice.application.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.application.message.service.GetMessageInternalXmlService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/message")
public class MessageInternalApiController {

  private final GetMessageInternalXmlService getMessageInternalXmlService;

  @PostMapping("/{messageId}/xml")
  GetMessageInternalXmlResponse getMessageXml(@PathVariable("messageId") String messageId) {
    return getMessageInternalXmlService.get(messageId);
  }
}
