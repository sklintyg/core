package se.inera.intyg.intygproxyservice.integrationtest;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
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

  public final static PersonDTO PROTECTED_PERSON_DTO = PersonDTO.builder()
      .personnummer("195401782395")
      .fornamn("Jan Petter")
      .mellannamn(null)
      .efternamn("Myrberg")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(true)
      .build();

  public final static Person PROTECTED_PERSON = Person.builder()
      .personnummer("195401782395")
      .fornamn("Jan Petter")
      .mellannamn(null)
      .efternamn("Myrberg")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(true)
      .build();

  public final static PersonDTO LILLTOLVAN = PersonDTO.builder()
      .personnummer("201212121212")
      .fornamn("Lilltolvan")
      .mellannamn(null)
      .efternamn("Tolvansson")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(false)
      .build();

  public final static PersonDTO TOLVAN = PersonDTO.builder()
      .personnummer("191212121212")
      .fornamn("Tolvan")
      .mellannamn(null)
      .efternamn("Tolvansson")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(false)
      .build();
}