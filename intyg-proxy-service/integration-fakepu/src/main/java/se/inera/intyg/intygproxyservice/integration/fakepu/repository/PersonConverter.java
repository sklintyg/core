package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddress;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddressInformation;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedName;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPerson;

public class PersonConverter {

  private PersonConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Person convert(ParsedPerson parsedPerson) {
    return Person.builder()
        .personnummer(
            PersonId.of(parsedPerson.getPersonalIdentity().getExtension())
        )
        .fornamn(
            name(parsedPerson.getName().getGivenName())
        )
        .mellannamn(
            name(parsedPerson.getName().getMiddleName())
        )
        .efternamn(
            name(parsedPerson.getName().getSurname())
        )
        .postadress(
            AddressConverter.convert(
                residentialAddress(parsedPerson.getAddressInformation())
            )
        )
        .postnummer(
            residentialAddress(parsedPerson.getAddressInformation()).getPostalCode()
        )
        .postort(
            residentialAddress(parsedPerson.getAddressInformation()).getCity()
        )
        .avliden(
            DeceasedConverter.convert(
                parsedPerson.getDeregistration()
            )
        )
        .sekretessmarkering(
            ProtectedConverter.convert(
                parsedPerson.isProtectedPersonIndicator(),
                parsedPerson.isProtectedPopulationRecord()
            )
        )
        .testIndicator(
            parsedPerson.isTestIndicator()
        )
        .build();
  }

  private static ParsedAddress residentialAddress(ParsedAddressInformation addressInformation) {
    return addressInformation != null && addressInformation.getResidentialAddress() != null
        ? addressInformation.getResidentialAddress()
        : ParsedAddress.builder().build();
  }

  private static String name(ParsedName parsedName) {
    return parsedName != null ? parsedName.getName() : null;
  }
}