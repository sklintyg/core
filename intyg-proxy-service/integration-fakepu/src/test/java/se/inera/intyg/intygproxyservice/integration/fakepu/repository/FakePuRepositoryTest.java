package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.PERSON_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;

class FakePuRepositoryTest {

  private FakePuRepository fakePuRepository;

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
    final var person = Person.builder().build();

    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(person)
    );
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithEmptyPersonnummer() {
    final var person = Person.builder()
        .personnummer("")
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(person)
    );
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithExistingPersonnummer() {
    final var person = Person.builder()
        .personnummer(PERSON_ID)
        .build();

    fakePuRepository.addPerson(person);

    assertThrows(IllegalArgumentException.class,
        () -> fakePuRepository.addPerson(person)
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