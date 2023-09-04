package se.inera.intyg.intygproxyservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.dto.PersonResponse;
import se.inera.intyg.intygproxyservice.service.PersonService;

@RestController()
@RequestMapping("/api/v1/person")
@AllArgsConstructor
public class PersonController {

  private final PersonService personService;

  @PostMapping("")
  PersonResponse findPerson(@RequestBody PersonRequest personRequest) {
    return personService.findPerson(personRequest);
  }
}
