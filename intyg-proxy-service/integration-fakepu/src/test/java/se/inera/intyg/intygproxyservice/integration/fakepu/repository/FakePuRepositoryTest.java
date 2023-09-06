package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;

class FakePuRepositoryTest {

  private FakePuRepository fakePuRepository;
  private String PERSON_ID = "191212121212";

  @BeforeEach
  void setUp() {
    fakePuRepository = new FakePuRepository();
  }

  @Test
  void shallAddPersonToRepository() {
    final var expectedPerson = Person.builder()
        .personnummer(PERSON_ID)
        .build();
    fakePuRepository.addPerson(expectedPerson);

    assertEquals(expectedPerson, fakePuRepository.getPerson(PERSON_ID));
  }

  @Test
  void shallThrowExceptionWhenAddingNullPerson() {
    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(null)
    );
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithNullPersonnummer() {
    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(
            Person.builder().build()
        )
    );
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithEmptyPersonnummer() {
    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(
            Person.builder()
                .personnummer("")
                .build()
        )
    );
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithExistingPersonnummer() {
    fakePuRepository.addPerson(
        Person.builder()
            .personnummer(PERSON_ID)
            .build()
    );

    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(
            Person.builder()
                .personnummer(PERSON_ID)
                .build()
        )
    );
  }

  @Test
  void shallTrowExceptionIfPassingNullPersonId() {
    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.getPerson(null)
    );
  }

  @Test
  void shallTrowExceptionIfPassingEmptyPersonId() {
    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.getPerson("")
    );
  }
}