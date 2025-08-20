package se.inera.intyg.intygproxyservice.citizen;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.citizen.service.CitizenService;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;

@RestController()
@RequestMapping("/api/v1/citizen")
@RequiredArgsConstructor
public class CitizenController {

  private final CitizenService citizenService;

  @PostMapping("")
  @PerformanceLogging(eventAction = "find-user", eventType = EVENT_TYPE_ACCESSED)
  CitizenResponse findCitizen(@RequestBody CitizenRequest citizenRequest) {
    return citizenService.findCitizen(citizenRequest);
  }
}