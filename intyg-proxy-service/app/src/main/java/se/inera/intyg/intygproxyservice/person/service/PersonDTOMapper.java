package se.inera.intyg.intygproxyservice.person.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;

@Component
public class PersonDTOMapper {

  @Value("${putjanst.testindicated.reclassify.active.except.ssn:}")
  private List<String> testIndicatedPersonIds;

  public PersonDTO toDTO(Person person) {
    if (person == null) {
      return null;
    }

    return PersonDTO.builder()
        .personnummer(person.getPersonnummer())
        .sekretessmarkering(person.isSekretessmarkering())
        .avliden(person.isAvliden())
        .fornamn(person.getFornamn())
        .mellannamn(person.getMellannamn())
        .efternamn(person.getEfternamn())
        .postadress(person.getPostadress())
        .postnummer(person.getPostnummer())
        .postort(person.getPostort())
        .testIndicator(testIndicatedPersonIds != null && !testIndicatedPersonIds.isEmpty()
            ? testIndicatedPersonIds.contains(person.getPersonnummer())
            : person.isTestIndicator())
        .build();
  }
}
