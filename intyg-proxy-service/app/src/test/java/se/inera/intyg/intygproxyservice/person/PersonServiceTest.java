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
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse.Status;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  private static final PersonRequest PU_REQUEST = PersonRequest.builder()
      .personId("191212121212")
      .build();

  @Mock
  private PuService puService;

  @InjectMocks
  private PersonService personService;


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
      assertThrows(IllegalArgumentException.class,
          () -> personService.findPerson(
              PersonRequest.builder()
                  .personId(null)
                  .build()
          )
      );
    }

    @Test
    void shallThrowExceptionIfPersonRequestContainsEmptyPersonId() {
      assertThrows(IllegalArgumentException.class,
          () -> personService.findPerson(
              PersonRequest.builder()
                  .personId("")
                  .build()
          )
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
      assertEquals(Status.FOUND, personResponse.getStatus());
    }

    @Test
    void shallReturnPersonFound() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(puResponseFound.getPerson(), personResponse.getPerson());
    }
  }

  @Nested
  class PersonNotFoundInPuService {

    private PuResponse puReponseNotFound;

    @BeforeEach
    void setUp() {
      puReponseNotFound = PuResponse.notFound();

      doReturn(puReponseNotFound)
          .when(puService)
          .findPerson(any(PuRequest.class));
    }

    @Test
    void shallReturnStatusNotFound() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(Status.NOT_FOUND, personResponse.getStatus());
    }

    @Test
    void shallNotReturnAnyPerson() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertNull(personResponse.getPerson());
    }
  }

  @Nested
  class PersonErrorInPuService {

    private PuResponse puResponseError;

    @BeforeEach
    void setUp() {
      puResponseError = PuResponse.error();

      doReturn(puResponseError)
          .when(puService)
          .findPerson(any(PuRequest.class));
    }

    @Test
    void shallReturnStatusError() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(Status.ERROR, personResponse.getStatus());
    }

    @Test
    void shallNotReturnAnyPerson() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertNull(personResponse.getPerson());
    }
  }
}