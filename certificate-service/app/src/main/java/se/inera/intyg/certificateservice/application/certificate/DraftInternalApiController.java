package se.inera.intyg.certificateservice.application.certificate;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_DELETION;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.DisposeObsoleteDraftsInternalService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/draft")
public class DraftInternalApiController {

  private final DisposeObsoleteDraftsInternalService disposeObsoleteDraftsInternalService;

  @PostMapping("/list")
  @PerformanceLogging(eventAction = "internal-list-obsolete-drafts", eventType = EVENT_TYPE_ACCESSED)
  ListObsoleteDraftsResponse listObsoleteDrafts(@RequestBody ListObsoleteDraftsRequest request) {
    return disposeObsoleteDraftsInternalService.list(request);
  }

  @DeleteMapping
  @PerformanceLogging(eventAction = "internal-dispose-obsolete-drafts", eventType = EVENT_TYPE_DELETION)
  DisposeObsoleteDraftsResponse deleteDrafts(@RequestBody DisposeObsoleteDraftsRequest request) {
    return disposeObsoleteDraftsInternalService.delete(request);
  }
}

