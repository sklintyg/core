package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.DeregistrationTypeConverter.deceased;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.NameTypeConverter.givenName;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.NameTypeConverter.middleName;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.NameTypeConverter.surname;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.PersonalIdentityTypeConverter.extension;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.ProtectedPersonConverter.protectedPerson;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.ResidentialAddressTypeConverter.city;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.ResidentialAddressTypeConverter.postalAddress;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.ResidentialAddressTypeConverter.postalCode;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.riv.strategicresourcemanagement.persons.person.v3.RequestedPersonRecordType;

@Component
public class GetPersonsForProfileResponseTypeConverter {

  public Person convert(RequestedPersonRecordType requestedPersonRecordType) {
    final var personRecord = requestedPersonRecordType.getPersonRecord();
    return Person.builder()
        .personnummer(
            extension(personRecord.getPersonalIdentity())
        )
        .fornamn(
            givenName(personRecord.getName())
        )
        .mellannamn(
            middleName(personRecord.getName())
        )
        .efternamn(
            surname(personRecord.getName())
        )
        .postadress(
            postalAddress(personRecord.getAddressInformation())
        )
        .postnummer(
            postalCode(personRecord.getAddressInformation())
        )
        .postort(
            city(personRecord.getAddressInformation())
        )
        .testIndicator(personRecord.isTestIndicator())
        .sekretessmarkering(
            protectedPerson(personRecord)
        )
        .avliden(
            deceased(personRecord.getDeregistration())
        )
        .build();
  }
}
