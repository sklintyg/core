package se.inera.intyg.intygproxyservice.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
