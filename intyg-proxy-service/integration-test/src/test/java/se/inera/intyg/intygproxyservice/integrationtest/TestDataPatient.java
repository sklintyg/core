package se.inera.intyg.intygproxyservice.integrationtest;

import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;

public class TestDataPatient {

  public final static PersonDTO DECEASED_TEST_INDICATED_PERSON = PersonDTO.builder()
      .personnummer("190503279812")
      .fornamn("Karl")
      .mellannamn(null)
      .efternamn("Svensson")
      .postadress("ASPGATAN 2")
      .postnummer("96137")
      .postort("BODEN")
      .avliden(true)
      .testIndicator(true)
      .sekretessmarkering(false)
      .build();
}
