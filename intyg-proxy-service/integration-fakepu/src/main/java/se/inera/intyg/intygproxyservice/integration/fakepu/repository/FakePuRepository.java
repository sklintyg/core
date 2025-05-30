package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;

@Repository
public class FakePuRepository {

  private final Map<String, Person> personMap = new HashMap<>();

  public void addPerson(Person person) {
    if (person == null || person.getPersonnummer() == null || person.getPersonnummer().id()
        .isBlank()) {
      throw new IllegalArgumentException(
          String.format("Invalid person: '%s'", person)
      );
    }

    if (personMap.containsKey(person.getPersonnummer().id())) {
      throw new IllegalArgumentException(
          String.format("Person with id '%s' already exists!", person.getPersonnummer())
      );
    }

    personMap.put(person.getPersonnummer().id(), person);
  }

  public Person getPerson(String personId) {
    if (personId == null || personId.isBlank()) {
      throw new IllegalArgumentException(
          String.format("Invalid personId: '%s'", personId)
      );
    }

    return personMap.get(personId);
  }
}