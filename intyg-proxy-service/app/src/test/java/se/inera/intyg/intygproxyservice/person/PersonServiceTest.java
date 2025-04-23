package se.inera.intyg.intygproxyservice.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import se.inera.intyg.intygproxyservice.common.HashUtility;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;
import se.inera.intyg.intygproxyservice.person.service.PersonDTOMapper;
import se.inera.intyg.intygproxyservice.person.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  private static final String PERSON_ID = "191212121212";
  private static final PersonRequest PU_REQUEST = PersonRequest.builder()
      .personId(PERSON_ID)
      .build();
  private static final PuResponse PERSON_RESPONSE = PuResponse.found(
      Person.builder()
          .personnummer(PersonId.of(PERSON_ID))
          .build()
  );
  private static final PersonDTO PERSON_DTO = PersonDTO.builder()
      .personnummer(PERSON_ID)
      .build();

  @Mock
  private PuService puService;
  @Mock
  private ObjectMapper objectMapper;
  @Mock
  private CacheManager cacheManager;
  @Mock
  private Cache cache;
  @Mock
  private PersonDTOMapper personDTOMapper;

  @InjectMocks
  private PersonService personService;

  @Nested
  class PersonInCache {

    @BeforeEach
    void setup() {
      when(personDTOMapper.toDTO(PERSON_RESPONSE.person()))
          .thenReturn(PERSON_DTO);
      when(cache.get(HashUtility.hash(PERSON_ID), String.class))
          .thenReturn(PERSON_RESPONSE.toString());
      when(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .thenReturn(cache);

      try {
        when(objectMapper.readValue(PERSON_RESPONSE.toString(), PuResponse.class))
            .thenReturn(PERSON_RESPONSE);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldNotMakeCallToPuServiceIfAllIdsAreInCache() {
      final var response = personService.findPerson(
          PersonRequest.builder()
              .personId(PERSON_ID)
              .build()
      );

      verify(puService, times(0)).findPersons(any());
      assertEquals(PERSON_DTO, response.getPerson());
    }
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

    @BeforeEach
    void setUp() {
      when(personDTOMapper.toDTO(PERSON_RESPONSE.person()))
          .thenReturn(PERSON_DTO);
      doReturn(PERSON_RESPONSE)
          .when(puService)
          .findPerson(any(PuRequest.class));
      when(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .thenReturn(cache);
    }

    @Test
    void shallReturnStatusFound() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(StatusDTOType.FOUND, personResponse.getStatus());
    }

    @Test
    void shallReturnPersonFound() {
      final var personResponse = personService.findPerson(PU_REQUEST);
      assertEquals(PERSON_DTO, personResponse.getPerson());
    }
  }

  @Nested
  class PersonNotFoundInPuService {

    @BeforeEach
    void setUp() {
      final var puReponseNotFound = PuResponse.notFound();

      when(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .thenReturn(cache);

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

      when(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .thenReturn(cache);

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