package se.inera.intyg.intygproxyservice.person;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonsResponse;
import se.inera.intyg.intygproxyservice.person.service.PersonsService;

@RestController()
@RequestMapping("/api/v1/persons")
@AllArgsConstructor
public class PersonsController {

  private final PersonsService personsService;

  @PostMapping("")
  @PerformanceLogging(eventAction = "find-persons", eventType = EVENT_TYPE_ACCESSED)
  PersonsResponse findPersons(@RequestBody PersonsRequest request) {
    return personsService.findPersons(request);
  }
}
