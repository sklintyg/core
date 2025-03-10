package se.inera.intyg.intygproxyservice.person;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;
import se.inera.intyg.intygproxyservice.person.service.PersonService;

@RestController()
@RequestMapping("/api/v1/person")
@AllArgsConstructor
public class PersonController {

  private final PersonService personService;

  @PostMapping("")
  @PerformanceLogging(eventAction = "find-person", eventType = EVENT_TYPE_ACCESSED)
  PersonResponse findPerson(@RequestBody PersonRequest personRequest) {
    return personService.findPerson(personRequest);
  }
}
