package se.inera.intyg.intygproxyservice.integration.pu.v5;

import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.CODE_PERSONAL_ID;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.riv.strategicresourcemanagement.persons.person.v5.AddressInformationType;
import se.riv.strategicresourcemanagement.persons.person.v5.DeregistrationType;
import se.riv.strategicresourcemanagement.persons.person.v5.IIType;
import se.riv.strategicresourcemanagement.persons.person.v5.NameType;
import se.riv.strategicresourcemanagement.persons.person.v5.PersonRecordType;
import se.riv.strategicresourcemanagement.persons.person.v5.RequestedPersonRecordType;
import se.riv.strategicresourcemanagement.persons.person.v5.ResidentialAddressType;

public class TestData {

  public static final String PERSON_ID_AS_PERSONNUMMER = "191212121212";
  public static final String PERSON_ID_AS_SAMORDNINGSNUMMER = "195401875769";
  public static final String GIVEN_NAME = "Tolvan";
  public static final String MIDDLE_NAME = "TT";
  public static final String SURNAME = "Tolvansson";
  public static final String CARE_OF = "c/o Tretton Trettonson";
  public static final String POSTAL_ADDRESS1 = "Fjortonkvarteret";
  public static final String POSTAL_ADDRESS2 = "Femtonvägen 15";
  public static final String POSTAL_CODE = "121314";
  public static final String CITY = "Tolvsund";
  public static final boolean DECEASED = true;
  public static final boolean PROTECTED_PERSON = true;
  public static final boolean TEST_INDICATED = true;

  public static Person PERSON = Person.builder()
      .personnummer(PersonId.of(PERSON_ID_AS_PERSONNUMMER))
      .fornamn(GIVEN_NAME)
      .mellannamn(MIDDLE_NAME)
      .efternamn(SURNAME)
      .postadress(POSTAL_ADDRESS1)
      .build();

  public static RequestedPersonRecordType requestedPersonRecordType() {
    final var requestedPersonRecordType = new RequestedPersonRecordType();
    requestedPersonRecordType.setPersonRecord(personRecordType());
    requestedPersonRecordType.setRequestedPersonalIdentity(personalIdentity());
    return requestedPersonRecordType;
  }

  public static PersonRecordType personRecordType() {
    final var personRecordType = new PersonRecordType();
    personRecordType.setPersonalIdentity(personalIdentity());
    personRecordType.setName(nameType());
    personRecordType.setAddressInformation(addressInformationType());
    personRecordType.setTestIndicator(TEST_INDICATED);
    personRecordType.setProtectedPersonIndicator(true);
    personRecordType.setDeregistration(deregistrationType());
    return personRecordType;
  }

  public static DeregistrationType deregistrationType() {
    final var deregistrationType = new DeregistrationType();
    deregistrationType.setDeregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED);
    return deregistrationType;
  }

  public static AddressInformationType addressInformationType() {
    final var addressInformationType = new AddressInformationType();
    addressInformationType.setResidentialAddress(residentialAddressType());
    return addressInformationType;
  }

  public static ResidentialAddressType residentialAddressType() {
    final var residentialAddressType = new ResidentialAddressType();
    residentialAddressType.setPostalAddress1(POSTAL_ADDRESS1);
    residentialAddressType.setPostalCode(Integer.parseInt(POSTAL_CODE));
    residentialAddressType.setCity(CITY);
    return residentialAddressType;
  }

  public static AddressInformationType addressInformationType(String careOf, String postalAddress1,
      String postalAddress2) {
    final var residentialAddressType = new ResidentialAddressType();
    residentialAddressType.setCareOf(careOf);
    residentialAddressType.setPostalAddress1(postalAddress1);
    residentialAddressType.setPostalAddress2(postalAddress2);

    final var addressInformationType = new AddressInformationType();
    addressInformationType.setResidentialAddress(residentialAddressType);

    return addressInformationType;
  }

  public static NameType nameType() {
    final var nameType = new NameType();
    nameType.setGivenName(
        GIVEN_NAME
    );
    nameType.setMiddleName(
        MIDDLE_NAME
    );
    nameType.setSurname(
        SURNAME
    );
    return nameType;
  }

  public static IIType personalIdentity() {
    final var iiType = new IIType();
    iiType.setRoot(CODE_PERSONAL_ID);
    iiType.setExtension(PERSON_ID_AS_PERSONNUMMER);
    return iiType;
  }
}