package se.inera.intyg.certificateservice.application.certificate;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_DELETION;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteStaleDraftsInternalService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/draft")
public class DraftInternalApiController {

  private final DeleteStaleDraftsInternalService deleteStaleDraftsInternalService;

  @PostMapping("/list")
  @PerformanceLogging(eventAction = "internal-list-stale-drafts", eventType = EVENT_TYPE_ACCESSED)
  ListStaleDraftsResponse listStaleDrafts(@RequestBody ListStaleDraftsRequest request) {
    return deleteStaleDraftsInternalService.list(request);
  }

  @DeleteMapping("/delete")
  @PerformanceLogging(eventAction = "internal-delete-certificate", eventType = EVENT_TYPE_DELETION)
  DeleteStaleDraftsResponse deleteDrafts(@RequestBody DeleteStaleDraftsRequest request) {
    return deleteStaleDraftsInternalService.delete(request);
  }
}

