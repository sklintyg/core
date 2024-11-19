package se.inera.intyg.intygproxyservice.authorization;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_CHANGE;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.authorization.dto.GetLastUpdateResponse;
import se.inera.intyg.intygproxyservice.authorization.service.GetLastUpdateService;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lastUpdate")
public class LastUpdateController {

  private final GetLastUpdateService getLastUpdateService;

  @GetMapping
  @PerformanceLogging(eventAction = "retrieve-last-hosp-update", eventType = EVENT_TYPE_ACCESSED)
  GetLastUpdateResponse getLastUpdate() {
    return getLastUpdateService.get();
  }
}
