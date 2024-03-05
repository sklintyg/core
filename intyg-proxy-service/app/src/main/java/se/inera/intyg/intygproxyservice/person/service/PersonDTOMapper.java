package se.inera.intyg.intygproxyservice.person.service;

import org.mapstruct.Mapper;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;

@Mapper(componentModel = "spring")
public interface PersonDTOMapper {

  PersonDTO toDTO(Person person);
}
