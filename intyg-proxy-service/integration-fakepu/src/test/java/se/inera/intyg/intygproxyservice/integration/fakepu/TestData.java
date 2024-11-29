package se.inera.intyg.intygproxyservice.integration.fakepu;

import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddress;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddressInformation;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedDeregistration;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedName;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedNameWrapper;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPerson;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPersonalIdentity;

public class TestData {

  public static final String PERSON_ID = "191212121212";
  public static final String PERSON_ID_2 = "201212121212";
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
  public static final String DEREGISTRATION_REASON_CODE_FOR_DECEASED = "AV";
  public static ParsedPerson PARSED_PERSON = ParsedPerson.builder()
      .personalIdentity(
          ParsedPersonalIdentity.builder()
              .extension(PERSON_ID)
              .build()
      )
      .name(
          ParsedNameWrapper.builder()
              .givenName(
                  ParsedName.builder()
                      .name(GIVEN_NAME)
                      .build()
              )
              .middleName(
                  ParsedName.builder()
                      .name(MIDDLE_NAME)
                      .build()
              )
              .surname(
                  ParsedName.builder()
                      .name(SURNAME)
                      .build()
              )
              .build()
      )
      .addressInformation(
          ParsedAddressInformation.builder()
              .residentialAddress(
                  ParsedAddress.builder()
                      .postalAddress2(POSTAL_ADDRESS2)
                      .postalCode(POSTAL_CODE)
                      .city(CITY)
                      .build()
              )
              .build()
      )
      .deregistration(
          ParsedDeregistration.builder()
              .deregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED)
              .build()
      )
      .protectedPersonIndicator(PROTECTED_PERSON)
      .testIndicator(TEST_INDICATED)
      .build();
}
