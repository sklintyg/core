package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.riv.strategicresourcemanagement.persons.person.v5.RequestedPersonRecordType;

@Component
public class GetPersonsForProfileResponseTypeConverterV5 {

  public Person convert(RequestedPersonRecordType requestedPersonRecordType) {
    final var personRecord = requestedPersonRecordType.getPersonRecord();
    return Person.builder()
        .personnummer(
            PersonalIdentityTypeConverter.extension(personRecord.getPersonalIdentity())
        )
        .fornamn(
            NameTypeConverter.givenName(personRecord.getName())
        )
        .mellannamn(
            NameTypeConverter.middleName(personRecord.getName())
        )
        .efternamn(
            NameTypeConverter.surname(personRecord.getName())
        )
        .postadress(
            ResidentialAddressTypeConverter.postalAddress(personRecord.getAddressInformation())
        )
        .postnummer(
            ResidentialAddressTypeConverter.postalCode(personRecord.getAddressInformation())
        )
        .postort(
            ResidentialAddressTypeConverter.city(personRecord.getAddressInformation())
        )
        .testIndicator(personRecord.isTestIndicator())
        .sekretessmarkering(
            ProtectedPersonConverter.protectedPerson(personRecord)
        )
        .avliden(
            DeregistrationTypeConverter.deceased(personRecord.getDeregistration())
        )
        .build();
  }
}
