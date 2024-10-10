package se.inera.intyg.intygproxyservice.authorization;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_CHANGE;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonResponse;
import se.inera.intyg.intygproxyservice.authorization.service.HandleCertificationPersonService;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificationPerson")
public class HandleCertificationPersonController {

  private final HandleCertificationPersonService handleCertificationPersonService;

  @PostMapping
  @PerformanceLogging(eventAction = "handle-certification-for-person", eventType = EVENT_TYPE_CHANGE)
  HandleCertificationPersonResponse handleCertificationForPerson(
      @RequestBody HandleCertificationPersonRequest request) {
    return handleCertificationPersonService.handle(request);
  }
}
