package se.inera.intyg.intygproxyservice.user;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.inera.intyg.intygproxyservice.user.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.user.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.user.service.CitizenService;

@RestController()
@RequestMapping("/api/v1/citizen")
@RequiredArgsConstructor
public class CitizenController {

  private final CitizenService citizenService;

  @PostMapping("")
  @PerformanceLogging(eventAction = "find-user", eventType = EVENT_TYPE_ACCESSED)
  CitizenResponse findUser(@RequestBody CitizenRequest citizenRequest) {
    return citizenService.findCitizen(citizenRequest);
  }
}