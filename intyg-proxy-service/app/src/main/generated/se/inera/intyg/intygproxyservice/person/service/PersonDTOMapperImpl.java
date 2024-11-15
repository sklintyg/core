package se.inera.intyg.intygproxyservice.person.service;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T09:40:00+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class PersonDTOMapperImpl implements PersonDTOMapper {

    @Override
    public PersonDTO toDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO.PersonDTOBuilder personDTO = PersonDTO.builder();

        personDTO.personnummer( person.getPersonnummer() );
        personDTO.sekretessmarkering( person.isSekretessmarkering() );
        personDTO.avliden( person.isAvliden() );
        personDTO.fornamn( person.getFornamn() );
        personDTO.mellannamn( person.getMellannamn() );
        personDTO.efternamn( person.getEfternamn() );
        personDTO.postadress( person.getPostadress() );
        personDTO.postnummer( person.getPostnummer() );
        personDTO.postort( person.getPostort() );
        personDTO.testIndicator( person.isTestIndicator() );

        return personDTO.build();
    }
}
