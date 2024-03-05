package se.inera.intyg.intygproxyservice.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;
import se.inera.intyg.intygproxyservice.person.service.PersonDTOMapperImpl;
import se.inera.intyg.intygproxyservice.person.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  private static final PersonRequest PU_REQUEST = PersonRequest.builder()
      .personId("191212121212")
      .build();

  @Mock
  private PuService puService;

  @InjectMocks
  private PersonService personService;

  @BeforeEach
  void setUp() {
    personService = new PersonService(puService, new PersonDTOMapperImpl());
  }

  @Nested
  class RequestValidation {

    @Test
    void shallThrowExceptionIfPersonRequestIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> personService.findPerson(null)
      );
    }

    @Test
    void shallThrowExceptionIfPersonRequestContainsNullPersonId() {
      final var personRequest = PersonRequest.builder()
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> personService.findPerson(personRequest)
      );
    }

    @Test
    void shallThrowExceptionIfPersonRequestContainsEmptyPersonId() {
      final var personRequest = PersonRequest.builder()
          .personId("")
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> personService.findPerson(personRequest)
      );
    }
  }

  @Nested
  class PersonFoundInPuService {

    private PuResponse puResponseFound;

    @BeforeEach
    void setUp() {
      puResponseFound = PuResponse.found(
          Person.builder().build()
      );

      doReturn(puResponseFound)
          .when(puService)
          .findPerson(any(PuRequest.class));
    }

    @Test
    void shallReturnStatusFound() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(StatusDTOType.FOUND, personResponse.getStatus());
    }

    @Test
    void shallReturnPersonFound() {
      final var expectedPerson = PersonDTO.builder().build();
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(expectedPerson, personResponse.getPerson());
    }
  }

  @Nested
  class PersonNotFoundInPuService {

    @BeforeEach
    void setUp() {
      final var puReponseNotFound = PuResponse.notFound();

      doReturn(puReponseNotFound)
          .when(puService)
          .findPerson(any(PuRequest.class));
    }

    @Test
    void shallReturnStatusNotFound() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(StatusDTOType.NOT_FOUND, personResponse.getStatus());
    }

    @Test
    void shallNotReturnAnyPerson() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertNull(personResponse.getPerson());
    }
  }

  @Nested
  class PersonErrorInPuService {

    @BeforeEach
    void setUp() {
      final var puResponseError = PuResponse.error();

      doReturn(puResponseError)
          .when(puService)
          .findPerson(any(PuRequest.class));
    }

    @Test
    void shallReturnStatusError() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(StatusDTOType.ERROR, personResponse.getStatus());
    }

    @Test
    void shallNotReturnAnyPerson() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertNull(personResponse.getPerson());
    }
  }
}