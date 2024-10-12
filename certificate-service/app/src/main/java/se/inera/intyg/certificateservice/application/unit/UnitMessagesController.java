package se.inera.intyg.certificateservice.application.unit;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitMessagesService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/unit/messages")
public class UnitMessagesController {

  private final GetUnitMessagesService getUnitMessagesService;

  @PostMapping
  @PerformanceLogging(eventAction = "retrieve-message-list-for-unit", eventType = EVENT_TYPE_ACCESSED)
  public GetUnitMessagesResponse getUnitMessages(
      @RequestBody GetUnitMessagesRequest request) {
    return getUnitMessagesService.get(request);
  }
}
