package se.inera.intyg.intygproxyservice.person.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;


class PersonDTOMapperTest {

  private static final Person PERSON_AVLIDEN = getPerson("PNR", true, false, false);
  private static final Person PERSON_NO_FLAGS = getPerson("PNR", false, false, false);
  private static final Person PERSON_SEKRETESS = getPerson("PNR", false, true, false);
  private static final Person PERSON_NOT_TEST_INDICATED = getPerson("PNR1", false, true, false);
  private static final Person PERSON_TEST_INDICATED = getPerson("PNR1", false, false, true);

  private static final PersonDTOMapper personDTOMapper = new PersonDTOMapper();

  @Test
  void shouldSetTestIndicatedFromPuFalseIfListOfReClassifyIsNull() {
    ReflectionTestUtils.setField(personDTOMapper, "testIndicatedPersonIds", null);
    assertFalse(personDTOMapper.toDTO(PERSON_NOT_TEST_INDICATED).isTestIndicator());
  }

  @Test
  void shouldSetTestIndicatedFromPuTrueIfListOfReClassifyIsNull() {
    ReflectionTestUtils.setField(personDTOMapper, "testIndicatedPersonIds", null);
    assertTrue(personDTOMapper.toDTO(PERSON_TEST_INDICATED).isTestIndicator());
  }

  @Test
  void shouldSetTestIndicatedFromPuFalseIfListOfReClassifyIsEmptyList() {
    ReflectionTestUtils.setField(personDTOMapper, "testIndicatedPersonIds",
        Collections.emptyList());
    assertFalse(personDTOMapper.toDTO(PERSON_NOT_TEST_INDICATED).isTestIndicator());
  }

  @Test
  void shouldSetTestIndicatedFromPuTrueIfListOfReClassifyIsEmptyList() {
    ReflectionTestUtils.setField(personDTOMapper, "testIndicatedPersonIds",
        Collections.emptyList());
    assertTrue(personDTOMapper.toDTO(PERSON_TEST_INDICATED).isTestIndicator());
  }

  @Nested
  class HasTestIndicatedPersonsToReClassify {

    @BeforeEach
    void setUp() {
      ReflectionTestUtils.setField(personDTOMapper, "testIndicatedPersonIds", List.of("PNR"));
    }

    @Test
    void shouldConvertPersonId() {
      assertEquals(
          PERSON_NO_FLAGS.getPersonnummer(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getPersonnummer()
      );
    }

    @Test
    void shouldConvertFirstName() {
      assertEquals(
          PERSON_NO_FLAGS.getFornamn(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getFornamn()
      );
    }

    @Test
    void shouldConvertLastName() {
      assertEquals(
          PERSON_NO_FLAGS.getEfternamn(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getEfternamn()
      );
    }

    @Test
    void shouldConvertMiddleName() {
      assertEquals(
          PERSON_NO_FLAGS.getMellannamn(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getMellannamn()
      );
    }

    @Test
    void shouldConvertPostaddress() {
      assertEquals(
          PERSON_NO_FLAGS.getPostadress(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getPostadress()
      );
    }

    @Test
    void shouldConvertPostnummer() {
      assertEquals(
          PERSON_NO_FLAGS.getPostnummer(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getPostnummer()
      );
    }

    @Test
    void shouldConvertPostort() {
      assertEquals(
          PERSON_NO_FLAGS.getPostort(),
          personDTOMapper.toDTO(PERSON_NO_FLAGS).getPostort()
      );
    }

    @Test
    void shouldConvertAvlidenTrue() {
      assertTrue(personDTOMapper.toDTO(PERSON_AVLIDEN).isAvliden());
    }

    @Test
    void shouldConvertAvlidenFalse() {
      assertFalse(personDTOMapper.toDTO(PERSON_NO_FLAGS).isAvliden());
    }

    @Test
    void shouldConvertSekretessTrue() {
      assertTrue(personDTOMapper.toDTO(PERSON_SEKRETESS).isSekretessmarkering());
    }

    @Test
    void shouldConvertSekretessFalse() {
      assertFalse(personDTOMapper.toDTO(PERSON_NO_FLAGS).isSekretessmarkering());
    }

    @Test
    void shouldConvertTestIndicatedTrueIfInListOfIds() {
      assertTrue(personDTOMapper.toDTO(PERSON_NO_FLAGS).isTestIndicator());
    }

    @Test
    void shouldConvertTestIndicatedFalseIfNotInListOfIds() {
      assertFalse(personDTOMapper.toDTO(PERSON_NOT_TEST_INDICATED).isTestIndicator());
    }
  }

  private static Person getPerson(String patientId, boolean avliden, boolean sekretess,
      boolean testIndicated) {
    return Person.builder()
        .personnummer(patientId)
        .postadress("Postadress")
        .postnummer("Postnummer")
        .postort("Postort")
        .fornamn("Fornamn")
        .efternamn("Efternamn")
        .mellannamn("Mellannamn")
        .avliden(avliden)
        .sekretessmarkering(sekretess)
        .testIndicator(testIndicated)
        .build();
  }
}