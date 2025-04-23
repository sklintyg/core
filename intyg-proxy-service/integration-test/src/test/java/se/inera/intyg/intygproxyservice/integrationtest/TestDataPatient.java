package se.inera.intyg.intygproxyservice.integrationtest;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;

public class TestDataPatient {

  public static final PersonDTO DECEASED_TEST_INDICATED_PERSON = PersonDTO.builder()
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

  public static final PersonDTO PROTECTED_PERSON_DTO = PersonDTO.builder()
      .personnummer("195401782395")
      .fornamn("Jan Petter")
      .mellannamn(null)
      .efternamn("Myrberg")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(true)
      .build();

  public static final Person PROTECTED_PERSON = Person.builder()
      .personnummer(PersonId.of("195401782395"))
      .fornamn("Jan Petter")
      .mellannamn(null)
      .efternamn("Myrberg")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(true)
      .build();

  public static final PersonDTO LILLTOLVAN = PersonDTO.builder()
      .personnummer("201212121212")
      .fornamn("Lilltolvan")
      .mellannamn(null)
      .efternamn("Tolvansson")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(false)
      .build();

  public static final PersonDTO TOLVAN = PersonDTO.builder()
      .personnummer("191212121212")
      .fornamn("Tolvan")
      .mellannamn(null)
      .efternamn("Tolvansson")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(false)
      .build();

  public static final Person ATHENA_REACT_ANDERSSON = Person.builder()
      .personnummer(PersonId.of("194011306125"))
      .fornamn("Athena")
      .mellannamn("React")
      .efternamn("Andersson")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(false)
      .build();

  public static final PersonDTO ATHENA_REACT_ANDERSSON_DTO = PersonDTO.builder()
      .personnummer("194011306125")
      .fornamn("Athena")
      .mellannamn("React")
      .efternamn("Andersson")
      .avliden(false)
      .testIndicator(false)
      .sekretessmarkering(false)
      .build();
}